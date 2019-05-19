#! /usr/bin/env python3
import re
import random
import argparse
import mechanicalsoup

from urllib.parse import urlparse
from mechanicalsoup.utils import LinkNotFoundError


arg_parser = argparse.ArgumentParser(description="A tool to identify web application vulnerabilities.")
arg_parser.add_argument('cmd', choices =['discover', 'test'],
                        help='Discover the pages and inputs on a web app or fuzz test the inputs on every page.')
arg_parser.add_argument('target', help='The web address to target.')
arg_parser.add_argument('--custom-auth', help='Authenticate to the app using the specified login profile.')
arg_parser.add_argument('--common-words', help='Provide a list of words to use for link guessing during fuzzing.')
arg_parser.add_argument('-e', '--extensions', help='Provide a list of common web extensions to add to the filenames without '
                                             'extensions to try to guess pages that are not linked to within the app.')
arg_parser.add_argument('-n', '--num-links', help='The number of links to randomly generate and try during fuzzing. '
                                                  'Defaults to 50,000.', nargs='?', const='50000')
ARGS = arg_parser.parse_args()
if not ARGS.num_links:
    ARGS.num_links = 50000
else:
    ARGS.num_links = int(ARGS.num_links)

# print(ARGS)

auth_profile = ARGS.custom_auth
username = None
password = None
protocol = None

protocol_pattern = re.compile('(^[a-z]+://)(.*)')
match = protocol_pattern.match(ARGS.target)
if match:
    protocol = match.group(1)
    ARGS.target = match.group(2)

if auth_profile:
    if auth_profile == 'dvwa':
        username = 'admin'
        password = 'password'
        if not protocol:
            protocol = 'http://'
    else:
        print('No profile match. Please use a known profile.')
        exit()

def load_names(path):
    files_file = open(path, 'r')
    files = []
    for file in files_file:
        files.append(file.rstrip('\n'))

    return files

def find_and_guess_links(browser):
    current_page = browser.absolute_url('')
    links = [current_page]
    site_root = protocol + urlparse(current_page).netloc
    crawled = set()
    crawled.add(site_root + '/dvwa/logout.php')

    if ARGS.common_words:
        files = load_names(ARGS.common_words)
        extensions = None
        if ARGS.extensions:
            extensions = load_names(ARGS.extensions)

        links_tried = set()
        guessed_links = []

        print("\nTrying {:,} generated links. Links found:".format(ARGS.num_links))
        for _ in range(ARGS.num_links):
            filename = random.choice(files)
            url = protocol + ARGS.target
            url += filename

            if extensions and '.' not in filename:
                ext = random.choice(extensions)
                url += '.' + ext

            if url not in links_tried:
                links_tried.add(url)
                resp = browser.open(url)
                if resp.status_code not in (400, 403, 404):
                    exp = re.compile('<title>Object not found!</title>')
                    match = re.search(exp, resp.text)

                    if not match and url not in crawled:
                        guessed_links.append(browser.absolute_url(url))
                        print(url)

        if len(guessed_links) == 0:
            print('No links successfully guessed. =(')

        links.extend(guessed_links)
    else:
        print('\nNo files specified. Fuzzer will not try to guess pages.\n')

    print("\nLinks found via crawling:")

    while links:
        current_page = links.pop(0)
        browser.open(current_page)
        crawled.add(current_page)
        print(current_page)

        try:
            for link in list(browser.links()):
                link = str(link)
                i1 = link.index('"') + 1
                i2 = link.index('"', i1)
                link = link[i1:i2]
                link = browser.absolute_url(link)
                if link.startswith(site_root):
                    if link not in crawled and link not in links:
                        links.append(link)
                # else:
                    #print("Offsite link: " + link)
        except Exception as e:
            ...

    crawled.remove(site_root + '/dvwa/logout.php')

    return crawled

browser = mechanicalsoup.StatefulBrowser(soup_config={'features': 'html5lib'},)
                # user_agent="""Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36
                # (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36""")

if username:
    url = protocol + ARGS.target
    browser.open(url)
    form = browser.select_form('form')
    browser['username'] = username
    browser['password'] = password
    browser.submit_selected()

site_links = find_and_guess_links(browser)

form_inputs = {}
get_inputs = {}
if site_links:
    print('\nPage inputs:')
    for link in site_links:
        browser.open(link)
        print(link)
        gets = []
        url_with_params = link.split('?')
        if len(url_with_params) > 1:
            params = url_with_params[1]
            param_pairs = params.split('&')
            for input in param_pairs:
                gets.append(input.split('=')[0])

            if gets:
                print("\tGET inputs:" + str(gets))
                get_inputs[link] = gets

        try:
            form = browser.select_form()
            exp = re.compile('name=["\'](\w+)["\']')
            match = re.findall(exp, str(form.form))
            if match:
                print('\tInput names:' + str(match))
                form_inputs[link] = match

        except LinkNotFoundError:
            ...
        except AttributeError:
            ...

cookie_jar = browser.get_cookiejar()
if len(cookie_jar) > 0:
    print("\nCookies accumulated during session:")
    for cookie in cookie_jar:
        print('\t', end='')
        print(cookie)


# Fuzz Tester
A tool to identify web application vulnerabilities.

<h2>Parts 1 & 2:</h2>
<strong>Setup</strong><br/>
<p>
This tool requires the installation of the mechanicalsoup package: <br/>
<code>sudo pip3 install mechanicalsoup</code>
</p>
<p>
<strong>Help</strong><br/>
You can view your options by using the -h or --help switch.<br/>
<code><pre>./fuzz.py -h
usage: fuzz.py [-h] [--custom-auth CUSTOM_AUTH] [--common-words COMMON_WORDS]
               [-e EXTENSIONS] [-n [NUM_LINKS]]
               {discover,test} target

A tool to identify web application vulnerabilities.

positional arguments:
  {discover,test}       Discover the pages and inputs on a web app or fuzz
                        test the inputs on every page.
  target                The web address to target.

optional arguments:
  -h, --help            show this help message and exit
  --custom-auth CUSTOM_AUTH
                        Authenticate to the app using the specified login
                        profile.
  --common-words COMMON_WORDS
                        Provide a list of words to use for link guessing
                        during fuzzing.
  -e EXTENSIONS, --extensions EXTENSIONS
                        Provide a list of common web extensions to add to the
                        filenames without extensions to try to guess pages
                        that are not linked to within the app.
  -n [NUM_LINKS], --num-links [NUM_LINKS]
                        The number of links to randomly generate and try
                        during fuzzing. Defaults to 50,000.

</pre></code>
</p>
<p>
<strong>Example Usage</strong>
<code><pre>
./fuzz.py discover http://192.168.1.9:10000/dvwa/ --custom-auth=dvwa --common-words=files_and_resources.txt
Trying 50,000 generated links. Links found:
http://192.168.1.9:10000/dvwa/robots.txt
http://192.168.1.9:10000/dvwa/about.php
http://192.168.1.9:10000/dvwa/config
http://192.168.1.9:10000/dvwa/index.php
http://192.168.1.9:10000/dvwa/.
http://192.168.1.9:10000/dvwa/favicon.ico
http://192.168.1.9:10000/dvwa/setup.php

Links found via crawling:
http://192.168.1.9:10000/dvwa/index.php
http://192.168.1.9:10000/dvwa/robots.txt
http://192.168.1.9:10000/dvwa/about.php
http://192.168.1.9:10000/dvwa/config
http://192.168.1.9:10000/dvwa/index.php
http://192.168.1.9:10000/dvwa/.
http://192.168.1.9:10000/dvwa/favicon.ico
http://192.168.1.9:10000/dvwa/setup.php
http://192.168.1.9:10000/dvwa/
http://192.168.1.9:10000/dvwa/instructions.php
http://192.168.1.9:10000/dvwa/vulnerabilities/brute/
http://192.168.1.9:10000/dvwa/vulnerabilities/exec/
http://192.168.1.9:10000/dvwa/vulnerabilities/csrf/
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=include.php
http://192.168.1.9:10000/dvwa/vulnerabilities/upload/
http://192.168.1.9:10000/dvwa/vulnerabilities/captcha/
http://192.168.1.9:10000/dvwa/vulnerabilities/sqli/
http://192.168.1.9:10000/dvwa/vulnerabilities/sqli_blind/
http://192.168.1.9:10000/dvwa/vulnerabilities/weak_id/
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_d/
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_r/
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_s/
http://192.168.1.9:10000/dvwa/security.php
http://192.168.1.9:10000/dvwa/phpinfo.php
http://192.168.1.9:10000/dvwa/docs/DVWA_v1.3.pdf
http://192.168.1.9:10000/dvwa/instructions.php?doc=PHPIDS-license
http://192.168.1.9:10000/dvwa/config/?C=N;O=D
http://192.168.1.9:10000/dvwa/config/?C=M;O=A
http://192.168.1.9:10000/dvwa/config/?C=S;O=A
http://192.168.1.9:10000/dvwa/config/?C=D;O=A
http://192.168.1.9:10000/dvwa/config/config.inc.php
http://192.168.1.9:10000/dvwa/config/config.inc.php.bak
http://192.168.1.9:10000/dvwa/config/config.inc.php.dist
http://192.168.1.9:10000/dvwa/instructions.php?doc=readme
http://192.168.1.9:10000/dvwa/instructions.php?doc=PDF
http://192.168.1.9:10000/dvwa/instructions.php?doc=changelog
http://192.168.1.9:10000/dvwa/instructions.php?doc=copying
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file1.php
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file2.php
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file3.php
http://192.168.1.9:10000/dvwa/security.php?phpids=on
http://192.168.1.9:10000/dvwa/security.php?test=%22&gt;&lt;script&gt;eval(window.name)&lt;/script&gt;
http://192.168.1.9:10000/dvwa/ids_log.php
http://192.168.1.9:10000/dvwa/phpinfo.php?=PHPB8B5F2A0-3C92-11d3-A3A9-4C7B08C10000
http://192.168.1.9:10000/dvwa/config/?C=N;O=A
http://192.168.1.9:10000/dvwa/config/?C=M;O=D
http://192.168.1.9:10000/dvwa/config/?C=S;O=D
http://192.168.1.9:10000/dvwa/config/?C=D;O=D
http://192.168.1.9:10000/dvwa/security.php?phpids=off

Page inputs:
http://192.168.1.9:10000/dvwa/vulnerabilities/exec/
	Input names:['ping', 'ip', 'Submit', 'user_token']
http://192.168.1.9:10000/dvwa/ids_log.php
	Input names:['clear_log']
http://192.168.1.9:10000/dvwa/instructions.php?doc=copying
	GET inputs:['doc']
http://192.168.1.9:10000/dvwa/vulnerabilities/sqli_blind/
	Input names:['id', 'Submit', 'user_token']
http://192.168.1.9:10000/dvwa/phpinfo.php?=PHPB8B5F2A0-3C92-11d3-A3A9-4C7B08C10000
	GET inputs:['']
http://192.168.1.9:10000/dvwa/phpinfo.php
http://192.168.1.9:10000/dvwa/config/config.inc.php
http://192.168.1.9:10000/dvwa/config/?C=M;O=D
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/config/?C=N;O=D
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_s/
	Input names:['guestform', 'txtName', 'mtxMessage', 'btnSign', 'btnClear', 'user_token']
http://192.168.1.9:10000/dvwa/security.php
	Input names:['security', 'seclev_submit', 'user_token']
http://192.168.1.9:10000/dvwa/security.php?phpids=on
	GET inputs:['phpids']
	Input names:['security', 'seclev_submit', 'user_token']
http://192.168.1.9:10000/dvwa/vulnerabilities/sqli/
	Input names:['id', 'Submit', 'user_token']
http://192.168.1.9:10000/dvwa/setup.php
	Input names:['create_db', 'user_token']
http://192.168.1.9:10000/dvwa/robots.txt
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file1.php
	GET inputs:['page']
http://192.168.1.9:10000/dvwa/favicon.ico
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=include.php
	GET inputs:['page']
http://192.168.1.9:10000/dvwa/config
http://192.168.1.9:10000/dvwa/vulnerabilities/brute/
	Input names:['username', 'password', 'Login', 'user_token']
http://192.168.1.9:10000/dvwa/config/config.inc.php.bak
http://192.168.1.9:10000/dvwa/instructions.php?doc=PDF
	GET inputs:['doc']
http://192.168.1.9:10000/dvwa/config/?C=M;O=A
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/config/?C=S;O=D
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/instructions.php
http://192.168.1.9:10000/dvwa/config/config.inc.php.dist
http://192.168.1.9:10000/dvwa/instructions.php?doc=readme
	GET inputs:['doc']
http://192.168.1.9:10000/dvwa/security.php?test=%22&gt;&lt;script&gt;eval(window.name)&lt;/script&gt;
	GET inputs:['test', 'gt;', 'lt;script', 'gt;eval(window.name)', 'lt;/script', 'gt;']
http://192.168.1.9:10000/dvwa/index.php
http://192.168.1.9:10000/dvwa/docs/DVWA_v1.3.pdf
http://192.168.1.9:10000/dvwa/vulnerabilities/weak_id/
http://192.168.1.9:10000/dvwa/config/?C=D;O=A
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/config/?C=D;O=D
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_d/
	Input names:['XSS', 'default']
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file2.php
	GET inputs:['page']
http://192.168.1.9:10000/dvwa/config/?C=N;O=A
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/about.php
http://192.168.1.9:10000/dvwa/instructions.php?doc=PHPIDS-license
	GET inputs:['doc']
http://192.168.1.9:10000/dvwa/vulnerabilities/xss_r/
	Input names:['XSS', 'name', 'user_token']
http://192.168.1.9:10000/dvwa/.
http://192.168.1.9:10000/dvwa/vulnerabilities/fi/?page=file3.php
	GET inputs:['page']
http://192.168.1.9:10000/dvwa/vulnerabilities/csrf/
	Input names:['password_current', 'password_new', 'password_conf', 'Change', 'user_token']
http://192.168.1.9:10000/dvwa/vulnerabilities/upload/
	Input names:['MAX_FILE_SIZE', 'uploaded', 'Upload', 'user_token']
http://192.168.1.9:10000/dvwa/security.php?phpids=off
	GET inputs:['phpids']
	Input names:['security', 'seclev_submit', 'user_token']
http://192.168.1.9:10000/dvwa/config/?C=S;O=A
	GET inputs:['C']
http://192.168.1.9:10000/dvwa/vulnerabilities/captcha/
	Input names:['step', 'password_current', 'password_new', 'password_conf', 'recaptcha_challenge_field', 'recaptcha_response_field', 'user_token', 'Change']
http://192.168.1.9:10000/dvwa/instructions.php?doc=changelog
	GET inputs:['doc']

Cookies accumulated during session:
	<Cookie PHPSESSID=u02k905hmhnacnmvknfiq51rc2 for 192.168.1.9/>
	<Cookie security=impossible for 192.168.1.9/dvwa>
</pre></code>
<p>
<p>
<strong>Omit Link Guessing</strong>
<code><pre>
./fuzz.py discover 192.168.1.9:10000 --custom-auth dvwa --common-words files_and_resources.txt -n 0

Trying 0 links. Links found:
No links successfully guessed. =(

Links found via crawling:
http://192.168.1.9:10000/dvwa/index.php
http://192.168.1.9:10000/dvwa/
...
Cookies accumulated during session:
	<Cookie PHPSESSID=ptd2smiubp7kvvpesaafre89e3 for 192.168.1.9/>
	<Cookie security=impossible for 192.168.1.9/dvwa>
</code></pre>
</p>

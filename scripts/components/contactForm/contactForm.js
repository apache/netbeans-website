/* global grecaptcha */

(function() {
    'use strict';
    
    var http = new XMLHttpRequest(),

        xhr = function(method, url, params, success, error, complete) {
            http.onreadystatechange = function() {
                if (http.readyState === 4 && http.status === 200) {
                    success(http.responseText);
                } else {
                    error(http.responseText);
                }
            };

            http.open(method, url, true);
            
            if (method === 'POST') {
                //Send the proper header information along with the request
                http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
                
                http.send('payload='+params);
            }
            
            complete();
        };
    
    document.querySelector('.submit').addEventListener('click', function() {
        var captchaValue = document.querySelector('#g-recaptcha-response').value;
        
        checkForm().then(function(isFormValid) {
            if (isFormValid && !!captchaValue) {
                var validFields = [];
                
                setValidationClass('.g-recaptcha', true);

                [].slice.call(document.querySelectorAll('#multi-form .validation-field.mf-valid [data-validators]')).forEach(function(field) {
                    validFields.push({
                        id: field.getAttribute('id'),
                        value: field.getAttribute('id') === 'captcha' ? captchaValue : field.value,
                        placeholder: field.getAttribute('data-placeholder'),
                        validators: field.getAttribute('data-validators').split(' ')
                    });
                });
                
                xhr('POST', "backend/contact.php?extraParam=contact", JSON.stringify({data: validFields}), function() {
                    document.querySelector('#responseMessage').innerText = 'Vielen Dank, die E-Mail wurde versandt.';
                    document.contactForm.reset();

                    removeClass(document.querySelector('#responseMessage'), 'error');
                    addClass(document.querySelector('#responseMessage'), 'success');
                }, function(res) {
                    if (res) {
                        document.querySelector('#responseMessage').innerHTML = res.statusText || "Oops da ist wohl was schiefgelaufen,<br />schreib uns bitte an kontakt@puls-webagentur.de";
                        removeClass(document.querySelector('#responseMessage'), 'success');
                        addClass(document.querySelector('#responseMessage'), 'error');
                    } else {
//                        console.log('Error: ', res || res.statusText);
                    }
                }, function() {
                    grecaptcha.reset();
                });
            } else {
                setValidationClass('.g-recaptcha', false);
            }
        });
    }, true);
}());
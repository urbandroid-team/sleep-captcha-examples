# Sleep as Android - Captcha Examples

[Sleep as Android](https://play.google.com/store/apps/details?id=com.urbandroid.sleep) is an Android app that helps you sleep better. To help you with waking up, Sleep as Android offers you tasks to solve when the alarm rings. The alarm will stop ringing once you solve the task. These tasks are called **CAPTCHAs**.

Sleep as Android has an open API for CAPTCHAs, so you can create your own CAPTCHAs to solve in the morning. This repository gives out some examples. Feel free to build upon it!

## API overview

To get an overview of the CAPTCHA API and the Captcha lifecycle, see [the docs on our website](http://sleep.urbandroid.org/documentation/developer-api/captcha-api/).

## Example documentation

There is one CAPTCHA in the example project at the moment - ReverseCaptcha.

### ReverseCaptcha

The heart of the CAPTCHA is the CaptchaActivity, in the example this is `ReverseCaptchaActivity.java`. This activity shows up when the Captcha is called by the alarm.




### TODO Na zaver:
In Sleep as Android, you choose the active CAPTCHA in **Settings > CAPTCHA**. Once you build a new CAPTCHA apk and install it, your CAPTCHA will also appear there.

* Captcha-support library
* [CAPTCHA 2016 Hackathon](http://sleep.urbandroid.org/captcha-hackathon-12016/)
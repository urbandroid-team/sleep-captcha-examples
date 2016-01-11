# Sleep as Android - Captcha Examples

[Sleep as Android](https://play.google.com/store/apps/details?id=com.urbandroid.sleep) is an Android app that helps you sleep better. To help you with waking up, Sleep as Android offers you tasks to solve when the alarm rings. The alarm will stop ringing once you solve the task. These tasks are called **CAPTCHAs**.

Sleep as Android has an open API for CAPTCHAs, so you can create your own CAPTCHAs to solve in the morning. This repository gives out some examples. Feel free to build upon it!

## API overview

To get an overview of the CAPTCHA API and the Captcha lifecycle, see [the docs on our website](http://sleep.urbandroid.org/documentation/developer-api/captcha-api/).

## ReverseCaptcha

There is one CAPTCHA in the example project at the moment - ReverseCaptcha. 

### ReverseCaptchaActivity.java and the AndroidManifest.xml

The heart of the CAPTCHA is the CaptchaActivity class, in the example this is `ReverseCaptchaActivity.java`. This activity shows up when the Captcha is called by the alarm. The activity is also declared in `AndroidManifest.xml` as 

    <activity 
        android:name=".ReverseCaptchaActivity" // this needs to be the same as the activity class name (except for the dot)
        android:launchMode="singleTop"
        android:label="@string/captcha_label"
        android:exported="true"
    >
            
`ReverseCaptchaActivity` holds a few things needed in every Captcha:

+ `private CaptchaSupport captchaSupport;` declaration which loads the captcha library (see [documentation of the Captcha library](https://github.com/urbandroid-team/sleep-captcha-support))
+ `onCreate()` hook which defines what happens when the activity is loaded
+ `onNewIntent()` hook which defines what happens when the activity is reopened
+ `captchaSupport = CaptchaSupportFactory.create(this);` in the onCreate(), onNewIntent() hook
+ `onDestroy()` hook which defines what happens when the activity is destroy
+ `captchaSupport.destroy();` in the onDestroy()

### Use of the CaptchaSupport library

#### Interaction with Sleep as Android

CaptchaSupport library makes it easy to interact with the main Sleep as Android application via the following methods:

+ `captchaSupport.solved()` -- call this when user solves the Captcha
+ `captchaSupport.unsolved()` -- call this when user fails to solve the Captcha (typically when he leaves the activity), will send an intent to start the alarm again
+ `captchaSupport.alive()` -- call this to reset the captcha timeout (typically when the user somehow interacts with the captcha)

#### Captcha modes

The Captcha activity can be started in one of three modes:

+ preview mode (when started from the **Sleep as Android > Settings > CAPTCHA > (your CAPTCHA) > Preview**)
+ configuration mode (when started from **Sleep as Android > Settings > CAPTCHA > (your CAPTCHA) > Settings**)
+ operational mode (when started by the alarm)

You can check in which mode has the activity been started by

+ `captchaSupport.isPreviewMode()` 
+ `captchaSupport.isConfigurationMode()` 
+ `captchaSupport.isOperationalMode()` 

and then make adjustments according to that. For example, when started in config mode, you might show a completely different layout than when starting in operational mode. The ReverseCaptcha doesn't do any of those checks as it doesn't offer a settings view (no `<action android:name="com.urbandroid.sleep.captcha.intent.action.CONFIG"/>` in the `AndroidManifest.xml`) and preview is the same as operational view.

### Where to find your CAPTCHA:
In Sleep as Android, you choose the active CAPTCHA in **Settings > CAPTCHA**. Once you build a new CAPTCHA apk and install it, your CAPTCHA will also appear there.

### Links
* [Captcha-support library](http://sleep.urbandroid.org/documentation/developer-api/captcha-api/)
* [CAPTCHA 2016 Hackathon](http://sleep.urbandroid.org/captcha-hackathon-12016/)
* In case of any problems, open an issue on github or drop us a line at support@urbandroid.org
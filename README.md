# FireLock (Fade-In Re-authentication Lock)

The library provides locks interfaces to conveniently and securely re-authenticate users. The library provides four interfaces with each providing a different screen transparency (**fadeInDelay**) and delay in the appearance of the lock (**lockDelay**). The four interfaces provided by the library are:

1. **imm-trans-imm-lock**- Remains transparent, No fadeInDelay, No lockDelay
2. **imm-dark-imm-lock**- Turns dark immediately, No fadeInDelay, No lockDelay
3. **grad-dark-imm-lock**- Turns dark gradually, has a fadeInDelay (default- 8 seconds), No lockDelay
4. **grad-dark-grad-lock**- Turns dark gradually, has a fadeInDelay (default- 8 seconds), has a lockDelay (default- 4 seconds)

<img src="https://github.com/lalitagarwal/Lock-Library/raw/master/images/Imm-Trans-PAT.gif" height="300px" />   |  <img src="https://github.com/lalitagarwal/Lock-Library/raw/master/images/Imm-Dark-PIN.gif" height="300px" /> | <img src="https://github.com/lalitagarwal/Lock-Library/raw/master/images/Grad-Dark-PIN.gif" height="300px" /> | <img src="https://github.com/lalitagarwal/Lock-Library/raw/master/images/Grad-Dark-Grad-Lock-PAT.gif" height="300px" />
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
imm-trans-imm-lock  |  imm-dark-imm-lock |  grad-dark-imm-lock |  grad-dark-grad-lock
(Pattern Lock)  |  (PIN Lock) |  (PIN Lock) |  (Pattern Lock)

The lock interfaces can be used with both PIN and Pattern locks. The fadeInDelay and the lockDelay can be customised depending on the need.

---

### How to use it?

In order to use the library in your app, call the PinLock/PatternLock activity  from the current activity:

```java
Intent pinLock= new Intent(this, PinLock.class); //call PatternLock.class to use the Pattern Lock
pinLock.putExtra("category", <category>);
pinLock.putExtra("fadeInDelay", <seconds>); //This is optional, default is 8 sec
pinLock.putExtra("lockDelay", <seconds>); //This is optional, default is 4 sec
```
---

### Demo
We provide an implementation of FireLock with [Itus](https://crysp.uwaterloo.ca/software/itus/), which is an Android library authenticating users using touch-based and keystroke-based classifiers. We integrate both FireLock and Itus with an open-source messaging application called [Signal](https://github.com/WhisperSystems/Signal-Android). The demo re-authenticates the users based on the classification score as provided by Itus. In case of a low score, the re-authentication prompt is activated whenever the user presses the send button to send a text message. The user is unable to send the message unless they re-authenticate themselves by entering the correct pass-code.

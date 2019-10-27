# About <a href="https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html"><img align="right" src="https://img.shields.io/badge/Java-8-B07219?logo=java" alt="Java 8" /></a>

PadlockJ is a text encryption application written in Java that uses the AES-128 algorithm.

![UI](img/ui.png)

# License <a href="https://github.com/UnexomWid/PadlockJ/blob/master/LICENSE"><img align="right" src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License: MIT" /></a>

PadlockJ was created by [UnexomWid](https://uw.exom.dev). It is licensed under the [MIT](https://github.com/UnexomWid/PadlockJ/blob/master/LICENSE) license.

# Releases

>Note: versions with the suffix **R** are considered stable releases, while those with the suffix **D** are considered unstable.

[v1.1R](https://github.com/UnexomWid/PadlockJ/releases/tag/v1.1R) - March 19, 2019

# Usage

PadlockJ lets you encrypt a one-line text message with a *case sensitive* key. After encryption, the message is encoded with `base64`, so you can safely send it via E-Mail or other means.

To decrypt a message, enter the `base64` encoded encrypted message, and the same key that was used for encryption. The decrypted message will be the same as the original.

# Note

PadlockJ lets you send messages securely via E-Mail or other means (provided everyone uses the same version of the application). However, it is not the best solution.

If you need something more secure, consider using something like [GnuPG](https://www.gnupg.org/).

>I am **not** responsible if you use this application to send very important messages, and they end up being cracked by someone who wasn't intended to be able to view them.
>Use PadlockJ at your own risk.
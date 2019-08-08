# KeystoreEncryption-Android
A simple application which describes and implements the mechanism of storing secret key using Keystore Security

The mechanism which is generally used by applications for storing very sensitive data like Credit Card details, Bank Account and such.
For that, the Cryptor.java is used to define and implement Keystore security mechanism. It makes use of the Keystore of the application along
with an ALIAS and Encrpytion algorithm.

For Encrypting,
a. Create an object of the Cryptor class.
b. Use a setIv method to init the cipher using a secret key in Cryptor class.
c. Encrypt the text using an encryption function defined in Cryptor class.
d. Store the Iv and encrypted text (The Iv can be made public and it does not cause any issue) in SharedPreference or Room Database.

For Decrypting,
a. Create an object of the Cryptor class.
b. Initialize the KeyStore instance.
c. Use the decrypt function by passing the encrypted text and the iv (stored in SharedPreference or Room Database).


So, with the above implementation, secrets are now safe in the KeyStore. Why it is probably the best method is because Keystore 
is very specific to the application. It cannot be retrieved and hence the text cannot be decrypted without it. Many applications 
which stores the Credit Card and other sensitive data of the users use this encryption method to keep it safe. Using room to store the 
iv and the encrypted text will further help in adding security.

<img src="https://github.com/varundwarkani/KeystoreEncryption-Android/blob/master/App%20Images/image1.jpg" width="25%"></img> <img src="https://github.com/varundwarkani/KeystoreEncryption-Android/blob/master/App%20Images/image2.jpg" width="25%"></img> <img src="https://github.com/varundwarkani/KeystoreEncryption-Android/blob/master/App%20Images/image3.jpg" width="25%"></img> <img src="https://github.com/varundwarkani/KeystoreEncryption-Android/blob/master/App%20Images/image4.jpg" width="25%"></img>

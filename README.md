# LockScreen
Android App that locks your phone and changes the passcode with an SMS sent from another mobile.
The app runs in the background and waits for a SMS from anyone containing 8 digits. The first 4 digits of the received message should be the current passcode of your mobile, and the last 4 digits are used to obtain the new passcode of your mobile.
#Current Logic
The new passcode is obtained by evaluating A ^ B where, 
A is a 4 digit number formed by the first 4 digits of the 8 digit SMS (current passcode).
B is a 4 digit number formed by the last 4 digits of the 8 digit SMS
^ is bitwise XOR operation.
#Future Improvements
The passcode obtaining logic should be customisable by the user. 

Ideally the Passcode logic should be kept as a secret by the user.

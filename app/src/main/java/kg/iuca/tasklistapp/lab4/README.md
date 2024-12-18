# Report on Lab Work 4

## Task 7: Timer Application

### Description:
A simple timer application was created, which counts down from 10 seconds. When the time reaches zero, it displays a message indicating that the time has run out. The user can restart the timer by pressing a button.

### Implementation:
- **State Management**: Two variables, `timeLeft` (for tracking the remaining time) and `isFinished` (for checking if the timer has finished), are used to manage the timer's state.
- **Timer Logic**: The `LaunchedEffect` composable is used to perform the countdown with a 1-second delay.
- **Button**: A button is provided to reset the timer back to 10 seconds and restart the countdown.

### Demo (gif)
<img src="/app/src/main/res/assets/timer-demo.gif" alt="Example GIF" width="200" />
# Report on Lab Work 3

## Task 5: Simple Calculator

### Description:
A simple calculator application was created, enabling the user to perform basic arithmetic operations (addition, subtraction, multiplication, division). The interface includes a text field for displaying the result and buttons for numbers and operations.

### Implementation:
- **Text Field**: Displays the input expression and result.
- **Buttons**: Buttons for digits and arithmetic operators (addition, subtraction, multiplication, division).
- **Logic**: Lambda functions handle button presses and evaluate expressions.
- **Functionality**:
    - **Clear**: Clears the current input.
    - **Equals**: Calculates the result of the expression entered.
    - **Error Handling**: Division by zero and invalid expressions are handled.

### Demo (gif)
<img src="/app/src/main/res/assets/calculator-demo.gif" alt="Example GIF" width="200" />

## Task 6: Multi-Screen Application

### Description:
Two screens were created: the first screen has a button “Go to second screen,” and the second screen has a button “Back.” Navigation between the screens was implemented using Compose Navigation.

### Implementation:
- **Navigation**: Used `NavController` and `NavHost` for managing navigation between screens.
- **First Screen**: Includes a button to navigate to the second screen.
- **Second Screen**: Contains a button to return to the first screen.

### Demo (gif)
<img src="/app/src/main/res/assets/navigation-demo.gif" alt="Example GIF" width="200" />
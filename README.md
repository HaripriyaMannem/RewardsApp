### Implementation of Rewards APP:

1. **User** creation and **transactions** of user happen by background thread.
    a) Uses created based on **team6** resources.
    b) For **1000** rupees transactions, user will get **10** points as redeem points.
    \
    &nbsp;
2. User can login by entering username and password:    
   a) When user tried to login if, user didn't create yet by thread, **system** will ask re login.
   b) if User created, can login successfully.
   \
   &nbsp;
3. User can redeem rewards **(Electronics/Fashion/Furniture)** categories, n number of times unless he quits by him/her self.
   \
   &nbsp;
4. As we are using background thread for the transactions the points may not accurate 100%.
  \
  &nbsp;
5. Finally displaying all gift cards availed by user.
Feature: View current market prices

    In order to make sensible buy and sell decisions
    As a portfolio holder
    I want to be able to see the current market prices for shares I am interested in

    Scenario: Should be able to see the current market prices when the market is open
      Given Patricia is an active trader on IEX
      And AAPL is currently trading at 175.00
      When Patricia requests the latest price for AAPL
      Then she should see a price of 175.00

    Scenario: Should show the previous closing price when the market is closed
      Given the market is closed
      And IBM closed at 185.00
      When Patric requests the latest price for IBM
      Then he should see a price of 185.00


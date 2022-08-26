[< Back](../README.md)

# Use Postman to send rShop API requests :twisted_rightwards_arrows:

### Prerequisites:
- [Postman ^9](https://www.postman.com)

### Steps

1) On the Postman, import the [collection](files/rshop-v1-dev.postman_collection.json) and [environment](files/rshop-v1-dev.postman_environment.json) files
    ```
    File > Import > Upload files
    Find and upload these two files:
    	- rshop/docs/files/rshop-v1-dev.postman_collection.json
    	- rshop/docs/files/rshop-v1-dev.postman_environment.json
    ```

2) On the Postman, activate the rshop-v1-dev environment
    ```
	Select the "rshop v1 - dev" environment in the combo-box in the upper right corner of the window
    ```
    
3) On the Postman, make a request
    ```
	Select the "rshop v1 - dev" collection
	Chose a endpoint request from de list
	Click on "Send" button
	(Many requests need authentication)
    ```

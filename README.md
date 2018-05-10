# OST KIT - Java Wrapper for the OST KIT API

A Java wrapper for the REST API of [OST KIT](https://kit.ost.com) which is currently under active development. This client implements version 0.9.2 of the [OST KIT REST API](https://dev.ost.com).

![Screenshot](ostkit.png)

A Branded Token economy must be setup first in order to use the API, see https://kit.ost.com for more information.

## Dependencies
This client uses `OK HTTP` for web requests. HMAC signing is implemented as a request interceptor.

## How to use the client

Create the OST KIT client using your Branded Token economy's `API key` and `API secret`.
```java
OstKitClient ost = OstKitClient.create("YOUR-API-KEY", "YOUR-API-SECRET");

/* USERS functions */
// Create a user named 'Ria'.
User user = ost.createUser("Ria");
System.out.println(user);

// ... and rename 'Ria' to 'Fred'.
user = ost.editUser(user.getUuid(), "Fred");
System.out.println(user);

// List users. NOTE: currently paging is not yet implemented, so only the first 25 users are returned. I know :)
List<User> users = ost.listUsers();
System.out.println(users);


/* TRANSACTION TYPES functions */
// Create a transaction type.
TransactionType transactionType = ost.createTransactionType("Clap", TransactionTypeKind.USER_TO_USER, 1, TransactionTypeCurrency.BT, 0); // user_to_user transaction of 1 BT named 'Clap'
System.out.println(transactionType);

// List transaction types.
List<TransactionType> transactionTypes = ost.listTransactionTypes();
System.out.println(transactionTypes);

// Execute a transaction type. This transfers a preconfigured amount of Branded Tokens from a user or company to another user or company.
String transactionUuid = ost.executeTransactionType("72b683c0-0877-4ff9-ba82-f687dfa81313", "0fe12919-73c0-46ef-990c-637b2f72e4be", "Clap");

// ... and retrieve the status of the transaction. Allow the transaction some time to get processed on the OpenST utility chains.
Transaction status = ost.getTransactionStatus(transactionUuid);
System.out.println(status);


/* AIRDROP functions */
// Airdrop tokens either to all users or only to the users that have never been airdropped before.
String airdropUuid = ost.airdrop(1, ListType.ALL); // airdrop 1 token to all users
System.out.println(airdropUuid);

// ... and retrieve the status of the airdrop.
AirdropStatus airdropStatus = ost.getAirdropStatus(airdropUuid);
System.out.println(airdropStatus);


/* NON-API functions */
// Retrieve a single user's token balance.
//This is not implemented by the OST KIT API, but is done via a workaround by renaming a user to its own username to get the user info.
String tokenBalance = ost.getUserTokenBalance(user.getUuid(), user.getName());
System.out.println(tokenBalance);

// ... or just pass the user object
tokenBalance = ost.getUserTokenBalance(user);
System.out.println(tokenBalance);
```

## OST KIT Java Client Roadmap

Some things to do, and ideas for potential features:

* Implement paging support and _fetch-all_ list functions, currently only the first 25 items are returned.
* Make web client properties like timeout, connection pool, etc configurable.
* Fully document the API and all function parameters and return types.

## Questions, feature requests and bug reports
If you have questions, have a great idea for the client or ran into issues using this client: please report them in the project's [Issues](https://github.com/realJayNay/ost-kit-java-client/issues) area.

Brought to you by [Jay Nay](https://github.com/realJayNay)

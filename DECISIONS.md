## Design Pattern

When tackling rules can change consistently throughout the year, maybe somewhat uniform and each may have different severity levels or protocols to deal with such, I felt like it made the most sense to have a base validation class that all subsequent rules could extend off of.

Therefore, if any rule needed specific tailoring or hitting an external service for additional reporting or tracking, each could do that

This also makes it very easy to build/integrate new rules because instead of starting from scratch you know what you will always need, and its preset

## Data Models
For the POJOs that each rule referrenced, instead of having a giant object, i wanted to break up the different aspects of the request. this makes visibility easier, as well as tracking what each piece of the data actually contains

The use of enums was to guarantee that the data that is getting validated is consistent, and if any future changes want to be implemented, that can simply be made in one place

## Conditional Rules
For conditional rules, i found it best to just reference the conditional (i.e. parentIncome for a dependent), inside the rule itself. this made it so that I wouldn't have to make specific rules for every use case, it keeps the core rules simple to the intent, and logically easy to follow

Doing it like this also makes it so rule execution time doesnt matter. Each rule runs in a modular fashion, not effecting the next or the before

## Severity Levels
When regarding severity, I figured, when it comes to scope, the validation service would make more since as a pass through to a severity service. While each rule has their own "severityLevel" method, for a microservice I thought it would be more appropriate to leave that responsibility with another service, but show where the call to that service would be

## Tradeoffs
Biggest tradeoff with this , besides doing a larger object for just the request, is that any change made in base will have to be implemented in all subsequent classes.

Interface + abstract class approach would potentially be a better scalable solution, as it wouldn't run into as many inheritability issues, but for a very simple rules engine like what i was given, I like the abstract class the most
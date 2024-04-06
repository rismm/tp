# User Guide

## SuperTracker

SuperTracker is a desktop app for managing a supermarket's inventory,
optimized for use via a Command Line Interface (CLI).

- [Quick Start](#quick-start)
- [Features](#features)
  - [Create a new item: `new`](#create-a-new-item-new)
  - [Delete an item: `delete`](#delete-an-item-delete)
  - [Increase quantity: `add`](#increase-quantity-add)
  - [Decrease quantity: `remove`](#decrease-quantity-remove)
  - [Update an item: `update`](#update-an-item-update)
  - [Find an item: `find`](#find-an-item-find)
  - [List all items: `list`](#list-all-items-list)
  - [Print report: `report`](#print-report-report)
  - [Quit the program: `quit`](#quit-the-program-quit)
  - [Saving inventory data](#saving-inventory-data)
  - [Loading inventory data](#loading-inventory-data)
  - [Editing the data file](#editing-the-data-file)

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure that you have Java 11 or above installed in your Computer.
2. Download the latest `SuperTracker.jar` from [here](https://github.com/AY2324S2-CS2113-T13-4/tp/releases).
3. Open a command terminal, cd into the folder you put the jar file in, and use the `java -jar SuperTracker.jar` command to run the application.
4. Type the command in the command box and press Enter to execute it.
5. Refer to the [Features](#features) below for details of each command and its format.

--------------------------------------------------------------------------------------------------------------------

## Features

> Notes about the command format:
> - Words in uppercase are parameters to be specified by the user
>   - e.g. in `add n/NAME q/QUANTITY`, `NAME` and `QUANTITY` are parameters which can be used as `add n/cheese q/100`
> - Items in square brackets are optional
>   - e.g. `list [q/] [p/]` can be used as `list q/`, `list p/`, `list` etc.
> - Parameters can be in any order
>   - e.g. if the command specifies `n/NAME q/QUANTITY`, `q/QUANTITY n/NAME` is also acceptable
> - Commands that do not take in parameters will ignore any additional parameters input by the user
>   - e.g. if the command specifies `quit now`, it will be interpreted as `quit`
> - If the command has multiple of the same parameters, it uses the first occurrence of that parameter
>   - e.g. if the command specifies `new n/Milk q/100 p/5 n/Cheese`, it will be interpreted as `new n/Milk q/100 p/5`
> - If any of the compulsory parameters are empty an error is thrown

<br>

### Create a new item: `new`
Create a new item in the inventory

Format: `new n/NAME q/QUANTITY p/PRICE [e/EXPIRY_DATE]`

- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If `NAME` already exists in the inventory, use the **update** command instead
- `QUANTITY` must be a non-negative integer
  - e.g. 1, 10, 100
- `PRICE` must be a non-negative integer or decimal number
  - e.g. 1, 0.20, 12.3, 12.345
- If the `PRICE` given has more than 2 decimal places, it will be rounded off to the nearest 2 decimal places
  - e.g. 12.345 ≈ 12.35
- `EXPIRY_DATE` must be a valid date in the format of `dd-mm-yyyy`
  - e.g. 05-10-2054, 16-07-2245

Example: `new n/Milk q/100 p/5`
```
Milk has been added to the inventory!
Quantity: 100
Price: $5.00
```
Example: `new n/Cheese q/50 p/1.23 e/15-06-2113`
```
Cheese has been added to the inventory!
Quantity: 50
Price: $1.23
Expiry Date: 15/06/2113
```

<br>

### Delete an item: `delete`
Delete an item from the inventory

Format: `delete n/NAME`
- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If `NAME` does not exist in the inventory, an error would be thrown

Example: `delete n/Milk`
```
Milk has been deleted!
```

<br>

### Increase quantity: `add`
Increase the quantity of an item

Format: `add n/NAME q/QUANTITY`
- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If `NAME` does not exist in the inventory, an error would be thrown
- `QUANTITY` must be a non-negative integer
  - e.g. 1, 10, 100

Example: `add n/Milk q/10`
```
10 Milk added to inventory!
Quantity: 110
```

<br>

### Decrease quantity: `remove`
Decrease the quantity of an item

Format: `remove n/NAME q/QUANTITY`
- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If `NAME` does not exist in the inventory, an error would be thrown
- `QUANTITY` must be a non-negative integer
  - e.g. 1, 10, 100
- If `QUANTITY` exceeds the current quantity of the item, the new quantity would be set to 0

Example: `remove n/Milk q/10`
```
10 Milk removed from inventory!
Quantity: 90
```

<br>

### Find an item: `find`
Find the item/items that contain the same name

Format: `find n/NAME`
- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If no items containing the same name are found, an error would be thrown

Example: `find n/Cheese` 
```
Here are your found items:
1. Name: Cheese cake
    Quantity: 100
    Price: $1.00
2. Name: Cheese
    Quantity: 100
    Price: $1.00
```
<br>

### Update an item: `update`
Update the quantity ,price and/or expiry of an item

Format: `update n/NAME [q/NEW_QUANTITY] [p/NEW_PRICE] [e/NEW_EXPIRY_DATE]`
- `NAME` is case-insensitive
  - e.g. `Cheese` will be interpreted as `cheese`
- If `NAME` does not exist in the inventory, an error would be thrown
- `NEW_QUANTITY` must be a non-negative integer
  - e.g. 1, 10, 100
- `NEW_PRICE` must be a non-negative integer or decimal number
  - e.g. 1, 0.20, 12.3, 12.345
- If the `NEW_PRICE` given has more than 2 decimal places, it will be rounded off to the nearest 2 decimal places
  - e.g. 12.345 ≈ 12.35
- `NEW_EXPIRY_DATE` must be a valid date in the format of `dd-mm-yyyy` or `nil` if expiry date is to be removed
  - e.g. 05-10-2054, 16-07-2245
- At least one of the optional parameters must be present

Example: `update n/Milk q/200 p/10 e/15-06-2113`
```
Milk has been successfully updated!
Quantity: 200
Price: $10.00
Expiry Date: 15/06/2113
```
Example: `update n/Milk q/50 p/9.99 e/nil`
```
Milk has been successfully updated!
Quantity: 50
Price: $9.99
```

<br>

### List all items: `list`
List all unique items in the inventory.
Output will be printed to the terminal and each row will contain the name of each item.

Format: `list [q/] [p/] [e/] [sq/] [sp/] [se/] [r/]`
- `q/` will list the quantity of each item in each row
- `p/` will list the price of each item in each row
- `e/` will list the expiry date of each item in each row if it contains a valid expiry date
- In each row, quantity, price and expiry date will be printed in the same order as the flags
  - e.g. if the command specifies `list p/ q/ e/`, the price will be printed first followed by quantity and expiry date
- `sq/` will list the items in order of ascending quantity
- `sp/` will list the items in order of ascending price
- `se/` will list the items in order of ascending date, items with no date will be displayed at the bottom of the list and sorted in ascending alphabetical order (A-Z) by default
- If the command has multiple sorting parameters, the list will be sorted according to the first sorting parameter
  - e.g. if the command specifies `list sq/ sp/ se/`, list will be sorted in order of ascending quantity
- If the command has no sorting parameters, the list will be sorted in ascending alphabetical order (A-Z) by default
- `r/` will reverse the order of the list

Example: `list q/ p/ e/ sp/ r/`
```
There are 3 unique items in your inventory:
1. Name: Milk    Quantity: 100    Price: $5.00
2. Name: Juice    Quantity: 300    Price: $4.00    Expiry Date: 12/03/2025
3. Name: Cheese    Quantity: 200    Price: $3.00
```

<br>

### Print report: `report`
List all items that match the requirements of the report

There are 2 types of reports:
1. **low stock** - list all items that are below a specified threshold provided by the user
2. **expiry** - list all items that are within a week of expiring or have already expired

The report will be printed to the terminal and will contain the name of each item
- If the report type is **low stock**: 
  - The quantity of each item would be listed
  - The report would be sorted in order of ascending quantity
- If the report type is **expiry**:
  - The expiry date of each item would be listed
  - The report would be sorted in order of ascending expiry date

Format: `report r/REPORT_TYPE [t/THRESHOLD_VALUE]`
- `r/` parameter that specifies the type of report. e.g. **low stock** or **expiry**
- `t/` parameter that specifies the threshold value to be compared to for **low stock report**. 
All items below the threshold value would be listed out.

> Note: If report type is **low stock** threshold value must be **included**. However, if report type is **expiry** 
threshold value must be **excluded**.


Example: `report r/low stock t/35`

```
There are 3 items low on stocks!
1. Name: Orange
   Quantity: 10
2. Name: Banana
   Quantity: 20
3. Name: Apple
   Quantity: 30
```
Example: `report r/expiry`

[//]: # (updates dates to be valid for PE day)
```
There are 2 items close to expiry!
1. Name: Orange
   Expiry Date: 08/04/2024 
2. Name: Apple
   Expiry Date: 09/04/2024
```

<br>

### Quit the program: `quit`
Quits the program

Format: `quit`

--------------------------------------------------------------------------------------------------------------------

### Saving inventory data
Inventory data in the program is saved to the hard disk in the file path `./data/` in the same directory that
the `SuperTracker.jar` file is in. Data will be saved automatically after any command that changes the item data in the inventory.

<br>

### Loading inventory data
Data that has been saved to the hard disk will be loaded and read by the program each time it is launched. 
If there is no data file, the program will the loading process.

<br>

### Editing the data file
Inventory data of the `SuperTracker` program is stored in a text file `items.txt` in the path `./data/` relative to 
the directory the `SuperTracker.jar` file is in. Users can edit and update the inventory data directly through the data file
if they would like to do so.
> Note: Edit the data file at your own caution. If the changes made to the data file are in an invalid format, the program
> will ignore those changes on its next load. The changes will be erased on the next automatic save, so do keep a backup of the data 
> file before editing.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Install the app in the other computer and overwrite the empty data file it creates 
with the file that contains the data of your previous SuperTracker inventory

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format                                                             | Examples
--------|--------------------------------------------------------------------|---------------
**New** | `new n/NAME q/QUANTITY p/PRICE e/EXPIRY_DATE`                      | e.g. `new n/Milk q/100 p/5 e/05-12-2113`
**Delete**| `delete n/NAME`                                                    | e.g. `delete n/Milk`
**Add**| `add n/NAME q/QUANTITY`                                            | e.g. `add n/Milk q/10`
**Remove**| `remove n/NAME q/QUANTITY`                                         | e.g. `remove n/Milk q/10`
**Update**| `update n/NAME [q/NEW_QUANTITY] [p/NEW_PRICE] [e/NEW_EXPIRY_DATE]` | e.g. `update n/Milk q/200 p/10 e/05-08-2113`
**Find**| `find n/NAME`                                                      | e.g. `find n/apple`
**List**| `list [q/] [p/] [e/] [sq/] [sp/] [se/] [r/]`                       | e.g. `list q/ p/ sp/ r/`
**Report**| `report r/REPORT_TYPE [t/THRESHOLD_VALUE]`                         | e.g. `report r/low stock t/10`
**Quit**| `quit`                                                             | e.g. `quit`

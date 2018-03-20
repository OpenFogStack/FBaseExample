# FBase Example

This example depicts a working FBase setup. It is based on the *Smart Parking Garage Management* scenario of the *Design and Implementation of a Fog Data Management System* Master's thesis from Jonathan Hasenburg.

## Scenario description (as written in the thesis):
*"The owner of a parking garage wants to deploy an application that automatically gives recommendations to visitors about the floor to be used. However, he does not want to install sensors on each parking spot due to a tight budget. Instead, a system that counts the number of cars that enter/exit a floor is capable of reaching the required accuracy while being a lot cheaper. For such a system, a sensor needs to be deployed at each car entrance and exit of a floor. In addition, there needs to be a display to visualize the capacity of this floor, and the capacity of all other floors. System wise, this requires an independent machine on each floor if received sensor input is supposed to be processed immediately.

The machines distribute their floor’s capacity to all other machines of the same parking garage. It is very important for the owner that no single point of failure exists, so the crash of a single machine on one floor should not stop the other machines from working. Furthermore, all data is permanently stored on a RAID inside the parking garage because the machines on each floor only have a limited storage capacity. With the permanently stored data, the parking garage owner can answer various questions, e.g., whether the garage has capacity problems in certain times of the year."*

## Components

![Components for this Example](fig/components.png)

All in all, the parking garage has three floors, as visualized above. Each floor node puts its own data into its own keygroup, in which the other floor nodes and the RAID node are listed as trigger nodes. Every datum emitted by a node simply contains the number of cars currently positioned on the belonging floor.

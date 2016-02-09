#Electric power system model
###General data for the project
This is the simple model of a dedicated electric power system. The model consist of two separate parts. There are model and dispatcher. In the model simulated working of power stations equipment, that participate in process of supporting the frequency in system.
The model gets daily generation schedules from dispatcher. Dispatcher gets data from power stations, consumers and calculates (for now gives stub) daily generation schedules.

Application consist of four packages, two models:

+ [epsm-core](https://github.com/epsm/epsm-core)
+ [epsd-core](https://github.com/epsm/epsd-core)

and two wrappers for models that helps intarcting models using JSON. Also wrappers have web interfaces.

+ [epsd-web](https://github.com/epsm/epsd-web)
+ [epsm-web](https://github.com/epsm/epsm-web)


Application launched on two servers on OpenShift:

+ [model](http://model-epsm.rhcloud.com/)
+ [dispatcher](http://dispatcher-epsm.rhcloud.com/app/history). 

The total project size is more than 16,000 source lines of code.

| technology    |  [epsm-core](https://github.com/epsm/epsm-core)    | [epsm-web](https://github.com/epsm/epsm-web)   | [epsd-core](https://github.com/epsm/epsd-core)| [epsd-web](https://github.com/epsm/epsd-web)|
|:-----------------|:---:|:---:|:---:|:---:|
| Java core        | yes | yes | yes | yes |
| Spring core      | no  | yes | no  | yes |
| MySQL            | no  | no  | no  | yes |
| JPA (Hibernate)  | no  | no  | no  | yes |
| JSP              | no  | yes | no  | yes |
| Spring webmvc    | no  | yes | no  | yes |
| JSON (fasterxml) | yes | yes | no  | yes |
| HTML, CSS        | no  | yes | no  | yes |
| Spring security  | no  | no  | no  | yes |
| SLF4J, Logback   | yes | yes | yes | yes |
| Junit, Mockito   | yes | yes | yes | yes |
| Power Mockito    | yes | yes | no  | no  |
| Spring test      | no  | yes | no  | yes |
| DbUnit           | no  | no  | no  | yes |

##epsd-core
####subject area description

For understanding it's necessary to read subject area description chapter from [epsm-core](https://github.com/epsm/epsm-core) firstly. 

Dispatcher calculating daily schedules of generation, to support the required reserves primary and secondary control. Additionally a spinning reserve must be supported in a normalized quantity, but not less than the largest generator in the power system to prevent blackout if generator will be turned off emergency. Because this functionality is not currently implemented and the dispather gives only a pre-calculated schedule (stub), a more detailed description will not be shown. Non stub model see in the package [epsm-core](https://github.com/epsm/epsm-core).

####realization
Here will be given class and sequence diagrams with sufficient detalization for the understanding of the program details. Also description of the program will be provided.

+ class diagram
![dispatcher class diagram](https://cloud.githubusercontent.com/assets/16285736/12745887/662dc0c4-c9a4-11e5-92da-d79530c9506a.jpg)

+ sequence diagram for the processes of registration power objects and getting their orders.
![dispatcher sequence diagram 1](https://cloud.githubusercontent.com/assets/16285736/12758883/174cc85e-c9e7-11e5-8b20-5fd6eae31508.jpg)

+ sequence diagram for the processes of calculation daily generation schedules and sending them to power stations.
![dispatcher sequence diagram 2](https://cloud.githubusercontent.com/assets/16285736/12745888/662eccee-c9a4-11e5-8f4d-afbbef1da775.jpg)

By words: Before an object can send and receive State Command, it must register with the dispatcher by sending it it's parameters. Each object from the simulation after a specified period of time in the simulation sends its State to the dispatcher. 

For now the objects PowerStation can get daily schedules from the dispatcher. 

Objects of subclasses of the Consumer can also get ConsumptionPermission, but still does not react on them.

The hierarchy of Message classes using which model and dispatcher interacts with each other see section realizatiom of the package implementation [epsm-core](https://github.com/epsm/epsm-core).

Unit-test coverage according to EclEmma is 85%.
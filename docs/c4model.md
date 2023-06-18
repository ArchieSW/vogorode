# C4 модель приложения
*Для просмотра диаграмм рекомендуется скачать расширение plantuml для intellij idea*

##### C1 - context level
```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

Person(handyman, "Handyman", "Site workers. There is a set of characteristics - what he can do (dig, plant potatoes, water the beds), where to be, a fixed schedule, payment per hour")
Person(rancher, "Rancher", "The person who owns the land.")

System(VOgorode, "VOgorode", "A service that provides data on free fields, employed workers, as well as information about workers and gardeners.")
Rel(handyman, VOgorode, "Gets information about free areas for work, book a place to work.")
Rel(rancher, VOgorode, "Publishes areas for work, exposes the price, appoints workers.")

@enduml
```

##### C2 - containers level
```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml

Person(handyman, "Handyman", "Site workers. There is a set of characteristics - what he can do (dig, plant potatoes, water the beds), where to be, a fixed schedule, payment per hour")
Person(rancher, "Rancher", "The person who owns the land.")

System_Boundary(c2, "VOgorode") {
    Container(handymanservice, "handyman-service", "Responsible for storing and processing handyman profiles as well as their bank accounts.")
    Container(landscapeservice, "landscape-service", "User management, job assignments, price management for jobs, rating, statistics collection")
    Container(rancherservice, "rancher-service", "Service serving plots, coordinates, field sizes, what is planted - when it will ripen, etc.")
}

ContainerDb(postgres, "PostgreSQL")
ContainerDb(mongo, "MongoDB")

Rel(handyman, handymanservice, "Uses", "http")
Rel(rancher, rancherservice, "Uses", "http")
Rel(handyman, landscapeservice, "Uses", "http")
Rel(rancher, landscapeservice, "Uses", "http")

Rel(handymanservice, postgres, "Uses", "jdbc")
Rel(landscapeservice, postgres, "Uses", "jdbc")
Rel(rancherservice, postgres, "Uses", "jdbc")

Rel(handymanservice, mongo, "Uses")
Rel(rancherservice, mongo, "Uses")
@enduml
```

##### C3 - components level
```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml

Person(handyman, "Handyman", "Site workers. There is a set of characteristics - what he can do (dig, plant potatoes, water the beds), where to be, a fixed schedule, payment per hour")
Person(rancher, "Rancher", "The person who owns the land.")

System_Boundary(c2, "VOgorode") {
    Container_Boundary(hsb, "handyman-service") {
        Component(handymancontroller, "HandymanController")
        Component(handymanservice, "HandymanService")
        Component(handymanusercontroller, "HandymanUserController")
        Component(handymanuserservice, "HandymanUserService")
        
        Component(accountcontroller, "AccountController")
        Component(accountservice, "AccountService")
        Component(bankservice, "BankService")
    }
    Container_Boundary(lsb, "landscape-service") {
        Component(usercontroller, "UserController")
        Component(userservice, "UserService")
        
        Component(ordercontroller, "OrderController")
        Component(orderservice, "OrderService")
        
        Component(fieldservice, "FieldService")
    }
    Container_Boundary(rsb, "rancher-service") {
        Component(ranchercontroller, "RancherController")
        Component(rancherservice, "RancherService")
        
        Component(gardenercontroller, "GardenerController")
        Component(gardenerservice, "GardenerService")
        
        Component(fieldservicers, "FieldService")
    }
}

Container_Boundary(bd, "Databases") {
    ContainerDb(postgres, "PostgreSQL")
    ContainerDb(mongo, "MongoDB")
}

Rel(handyman, handymancontroller, "Uses", "http")
Rel(handyman, handymanusercontroller, "Uses", "http")
Rel(handyman, ordercontroller, "Uses", "http")
Rel(handyman, usercontroller, "Uses", "http")
Rel(handyman, accountcontroller, "Uses", "http")
Rel(rancher, ranchercontroller, "Uses", "http")
Rel(rancher, usercontroller, "Uses", "http")
Rel(rancher, gardenercontroller, "Uses", "http")


Rel(handymancontroller, handymanservice, "Uses", "Java method call")
Rel(handymanusercontroller, handymanuserservice, "Uses", "Java method call")

Rel(handymanservice, usercontroller, "Uses", "http")

Rel(accountcontroller, accountservice, "Uses", "Java method call")
Rel(accountcontroller, bankservice, "Uses", "Java method call")
Rel(accountservice, bankservice, "Uses", "Java method call")


Rel(usercontroller, userservice, "Uses", "Java method call")

Rel(ordercontroller, orderservice, "Uses", "Java method call")
Rel(orderservice, userservice, "Uses", "Java method call")
Rel(orderservice, fieldservice, "Uses", "Java method call")


Rel(ranchercontroller, rancherservice, "Uses", "Java method call")
Rel(rancherservice, usercontroller, "Uses", "Java method call")


Rel(gardenercontroller, gardenerservice, "Uses", "Java method call")
Rel(gardenerservice, fieldservicers, "Uses", "Java method call")


Rel(handymanuserservice, postgres, "Uses", "jdbc")
Rel(accountservice, postgres, "Uses", "jdbc")
Rel(userservice, postgres, "Uses", "jdbc")
Rel(orderservice, postgres, "Uses", "jdbc")
Rel(fieldservice, postgres, "Uses", "jdbc")
Rel(fieldservicers, postgres, "Uses", "jdbc")
Rel(gardenerservice, postgres, "Uses", "jdbc")

Rel(handymanservice, mongo, "Uses")
Rel(rancherservice, mongo, "Uses")
@enduml
```

##### C4 - code level
```plantuml
@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml

Person(handyman, "Handyman", "Site workers. There is a set of characteristics - what he can do (dig, plant potatoes, water the beds), where to be, a fixed schedule, payment per hour")
Person(rancher, "Rancher", "The person who owns the land.")

System_Boundary(c2, "VOgorode") {
    Container_Boundary(hsb, "handyman-service") {
        Component(handymancontroller, "HandymanController")
        Component(handymanservice, "HandymanService")
        Component(handymanrepository, "HandymanRepository")
        Component(handymanusercontroller, "HandymanUserController")
        Component(handymanuserservice, "HandymanUserService")
        Component(handymanuserepository, "HandymanUserRepository")
        
        Component(accountcontroller, "AccountController")
        Component(accountservice, "AccountService")
        Component(accountrepository, "AccountRepository")
        Component(bankservice, "BankService")
        Component(bankrepository, "BankRepository")
    }
    Container_Boundary(lsb, "landscape-service") {
        Component(usercontroller, "UserController")
        Component(userservice, "UserService")
        Component(userrepository, "UserRepository")
        Component(usertyperepository, "UserTypeRepository")
        
        Component(ordercontroller, "OrderController")
        Component(orderservice, "OrderService")
        Component(orderrepository, "OrderRepository")
        
        Component(fieldservice, "FieldService")
        Component(fieldrepository, "FieldRepository")
    }
    Container_Boundary(rsb, "rancher-service") {
        Component(ranchercontroller, "RancherController")
        Component(rancherservice, "RancherService")
        Component(rancherrepository, "RancherRepository")
        
        Component(gardenercontroller, "GardenerController")
        Component(gardenerservice, "GardenerService")
        Component(gardenerrepository, "GardenerRepository")
        
        Component(fieldrepositoryrs, "FieldRepository")
        Component(fieldservicers, "FieldService")
    }
}

Container_Boundary(bd, "Databases") {
    ContainerDb(postgres, "PostgreSQL")
    ContainerDb(mongo, "MongoDB")
}

Rel(handyman, handymancontroller, "Uses", "http")
Rel(handyman, handymanusercontroller, "Uses", "http")
Rel(handyman, ordercontroller, "Uses", "http")
Rel(handyman, usercontroller, "Uses", "http")
Rel(handyman, accountcontroller, "Uses", "http")
Rel(rancher, ranchercontroller, "Uses", "http")
Rel(rancher, usercontroller, "Uses", "http")
Rel(rancher, gardenercontroller, "Uses", "http")

Rel(handymancontroller, handymanservice, "Uses", "Java method call")
Rel(handymanusercontroller, handymanuserservice, "Uses", "Java method call")
Rel(handymanservice, handymanrepository, "Uses", "Java method call")

Rel(handymanservice, usercontroller, "Uses", "http")

Rel(accountcontroller, accountservice, "Uses", "Java method call")
Rel(accountcontroller, bankservice, "Uses", "Java method call")
Rel(accountservice, accountrepository, "Uses", "Java method call")
Rel(accountservice, handymanuserepository, "Uses", "Java method call")
Rel(accountservice, bankservice, "Uses", "Java method call")

Rel(bankservice, bankrepository, "Uses", "Java method call")

Rel(usercontroller, userservice, "Uses", "Java method call")
Rel(userservice, userrepository, "Uses", "Java method call")
Rel(userservice, usertyperepository, "Uses", "Java method call")

Rel(ordercontroller, orderservice, "Uses", "Java method call")
Rel(orderservice, userservice, "Uses", "Java method call")
Rel(orderservice, fieldservice, "Uses", "Java method call")
Rel(orderservice, orderrepository, "Uses", "Java method call")

Rel(fieldservice, fieldrepository, "Uses", "Java method call")

Rel(ranchercontroller, rancherservice, "Uses", "Java method call")
Rel(rancherservice, rancherrepository, "Uses", "Java method call")
Rel(rancherservice, usercontroller, "Uses", "Java method call")


Rel(gardenercontroller, gardenerservice, "Uses", "Java method call")
Rel(gardenerservice, gardenerrepository, "Uses", "Java method call")
Rel(gardenerservice, fieldservicers, "Uses", "Java method call")

Rel(fieldservicers, fieldrepositoryrs, "Uses", "Java method call")
Rel(fieldservicers, gardenerrepository, "Uses", "Java method call")

Rel(handymanuserepository, postgres, "Uses", "jdbc")
Rel(accountrepository, postgres, "Uses", "jdbc")
Rel(userrepository, postgres, "Uses", "jdbc")
Rel(orderrepository, postgres, "Uses", "jdbc")
Rel(fieldrepository, postgres, "Uses", "jdbc")
Rel(fieldrepositoryrs, postgres, "Uses", "jdbc")
Rel(gardenerrepository, postgres, "Uses", "jdbc")

Rel(handymanrepository, mongo, "Uses")
Rel(rancherrepository, mongo, "Uses")
@enduml
```

# DeliveryGo
<div style="text-align: center;">
  <img src="app-icon1.png" alt="Icona dell'applicazione" width="150"/>
</div>

## Descrizione del progetto

**DeliveryGo** è un sistema avanzato di logistica intelligente che ottimizza la gestione delle consegne in tempo reale. Progettato per migliorare l'efficienza dei camionisti e ridurre i tempi di consegna, **DeliveryGo** integra l'ottimizzazione delle rotte, il tracciamento in tempo reale, e la gestione delle anomalie.

Il sistema è composto da un'applicazione mobile per i camionisti, un backend basato su microservizi, e una serie di algoritmi di ottimizzazione che utilizzano modelli predittivi per gestire le rotte, la capacità dei veicoli, le finestre temporali di consegna e la disponibilità dei camionisti.

### Funzionalità principali:
- **Ottimizzazione delle rotte**: Calcolo delle migliori rotte in base a vincoli di tempo, capacità del veicolo e disponibilità dei camionisti..
- **Gestione delle anomalie**: Rilevamento e gestione di situazioni anomale, come guasti ai veicoli o deviazioni dalla rotta.
- **Tracciamento GPS in tempo reale**: Monitoraggio continuo della posizione dei veicoli tramite GPS, con aggiornamenti sulla mappa.
- **Gestione ordini**: Assegnazione da parte dell'admin degli ordini ai camionisti e gestione delle loro attività (accettazione e completamento delle tratte).

### Obiettivi:
- **Ottimizzare i percorsi** per ridurre il consumo di carburante e migliorare l'efficienza.
- **Fornire notifiche in tempo reale** ai camionisti e agli admin su eventi critici come guasti o deviazioni.
- **Facilitare la gestione delle consegne** per gli amministratori, con una visualizzazione completa delle rotte e delle attività.

---

## Architettura del Sistema

Il sistema è progettato con un'architettura **a microservizi**, che consente una gestione scalabile e modulare. I vari componenti sono sviluppati come servizi indipendenti che comunicano tramite API RESTful.

### Componenti principali:

1. **PositionService**: Gestisce il tracciamento GPS dei veicoli, riceve e aggiorna le posizioni in tempo reale.
2. **DeliveryService**: Si occupa della gestione degli ordini e delle rotte, inclusa l'assegnazione dei veicoli agli ordini.
3. **VehicleRoutingService**: Responsabile del calcolo delle rotte ottimizzate per i veicoli, utilizzando algoritmi come OR-Tools per risolvere il CVRPTW (Capacitated Vehicle Routing Problem with Time Windows).
4. **NotificationService**: Gestisce l'invio di notifiche push ai camionisti e agli admin, incluse le notifiche di aggiornamento delle rotte e segnalazione di anomalie.
5. **UserService**: Gestisce la registrazione, l'autenticazione e l'autorizzazione degli utenti (camionisti, admin).

### Tecnologia utilizzata:
- **Backend**: Java (Spring Boot) per la logica di business e il supporto delle API RESTful.
- **Frontend**: React Native per l'applicazione mobile dei camionisti.
- **Database**: MySQL o PostgreSQL per la gestione dei dati relativi agli ordini e ai veicoli.
- **Ottimizzazione rotte**: OR-Tools, GraphHopper per il calcolo delle rotte.
- **Tracciamento GPS**: MQTT per l'invio della posizione in tempo reale.

---

## Funzionalità



## Frontend

L'app mobile, sviluppata con **React Native**, consente ai camionisti di:

- Visualizzare la rotta ottimizzata.
- Monitorare lo stato degli ordini.
- Ricevere notifiche push in caso di deviazioni o anomalie.
- Aggiornare la propria posizione in tempo reale.
  
L'app comunica con il **backend** tramite API RESTful sicure. Il sistema di notifica è integrato con **Firebase Cloud Messaging (FCM)** per inviare alert sui cambiamenti in tempo reale.



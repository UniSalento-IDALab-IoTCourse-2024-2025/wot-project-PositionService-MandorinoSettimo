# DeliveryGo

<div style="text-align: center;">
  <img src="app-icon.png" alt="Icona dell'applicazione" width="250"/>
</div>

## Descrizione del progetto

**DeliveryGo** è un sistema avanzato di logistica intelligente che ottimizza la gestione delle consegne in tempo reale. Progettato per migliorare l'efficienza dei camionisti e ridurre i tempi di consegna, **DeliveryGo** integra l'ottimizzazione delle rotte, il tracciamento in tempo reale, e la gestione delle anomalie.

Il sistema è composto da un'applicazione mobile per i camionisti, un backend basato su microservizi, e un algoritmo di ottimizzazione che utilizza modelli predittivi per gestire le rotte, la capacità dei veicoli, le finestre temporali di consegna e la disponibilità dei camionisti.

### Funzionalità principali:
- **Ottimizzazione delle rotte**: Calcolo delle migliori rotte in base a vincoli di tempo, capacità del veicolo e disponibilità dei camionisti.
- **Gestione delle anomalie**: Rilevamento e gestione di situazioni anomale, come guasti ai veicoli o deviazioni dalla rotta.
- **Tracciamento GPS in tempo reale**: Monitoraggio continuo della posizione dei veicoli tramite GPS, con aggiornamenti sulla mappa.
- **Gestione ordini**: Assegnazione da parte dell'admin degli ordini ai camionisti e gestione delle loro attività (accettazione e completamento delle tratte).

### Obiettivi:
- **Ottimizzare i percorsi** per permettere all'admin di gestire gli ordini in maniera ottimale.
- **Fornire notifiche in tempo reale** ai camionisti e agli admin su eventi critici come guasti o deviazioni, o avvisare l'utente di un'assegnazione.
- **Gestione anomalie** per permettere all'admin di intervenire subito in caso di guasto.

---

## Architettura del Sistema

Il sistema è progettato con un'architettura **a microservizi**, che consente una gestione scalabile e modulare. I vari componenti sono sviluppati come servizi indipendenti che comunicano tramite API RESTful.

<div style="text-align: center;">
  <img src="architettura (1).png" alt="Architettura utilizzata" width="1000"/>
</div>

### Componenti principali:

1. **PositionService**: Gestisce tutto ciò che riguarda l'utente, incluse le informazioni di registrazione, aggiornamento del profilo e il tracciamento GPS.
2. **DeliveryService**: Si occupa della gestione degli ordini e delle rotte, inclusa l'assegnazione dei veicoli agli ordini e l'ottimizzazione del percorso.
3. **VehicleRoutingService**: Responsabile del calcolo delle rotte ottimizzate per i veicoli, utilizzando algoritmi come OR-Tools per risolvere il CVRPTW (Capacitated Vehicle Routing Problem with Time Windows).
4. **NotificationService**: Gestisce l'invio di notifiche push ai camionisti e agli admin, incluse le notifiche di aggiornamento delle rotte e segnalazione di anomalie.

### Tecnologia utilizzata:
- **Backend**: Java (Spring Boot) per la logica di business e il supporto delle API RESTful.
- **Frontend**: React Native per l'applicazione mobile dei camionisti.
- **Database**: MongoDB per la gestione dei dati relativi agli ordini e ai veicoli.
- **Ottimizzazione rotte**: OR-Tools, GraphHopper per il calcolo delle rotte.
- **Tracciamento GPS**: MQTT per l'invio della posizione in tempo reale.

---


## Frontend

L'app mobile, sviluppata con **React Native**, consente ai camionisti di:

- Gestire il proprio profilo.
- Visualizzare la rotta ottimizzata.
- Ricevere notifiche push in caso di anomalie.
- Aggiornare la propria posizione in tempo reale.

Mentre consente agli admin di:

- Creare e gestire veicoli, ordini e rotte.
- Assegnare tratte ai camionisti.
- Visualizzare lo stato di ordini e veicoli.
- Intervenire, qualora fosse presente un'anomalia, in maniera immediata.
  
L'app comunica con il **backend** tramite API RESTful sicure. Il sistema di notifica è integrato con **Firebase Cloud Messaging (FCM)** per inviare alert sui cambiamenti in tempo reale.

---

## Repository dei Componenti

### Componenti del Sistema:
- **PositionService**: [Repository PositionService](https://github.com/UniSalento-IDALab-IoTCourse-2024-2025/wot-project-PositionService-MandorinoSettimo)
- **DeliveryService**: [Repository DeliveryService](https://github.com/tuo-nome/DeliveryService)
- **VehicleRoutingService**: [Repository VehicleRoutingService](https://github.com/tuo-nome/VehicleRoutingService)
- **NotificationService**: [Repository NotificationService](https://github.com/tuo-nome/NotificationService)

### Repository Frontend:
- **Frontend**: [Repository Frontend](https://github.com/tuo-nome/Frontend)

---

## Approfondimento su **PositionService**

Il **PositionService** gestisce tutto ciò che riguarda gli utenti del sistema, incluse le operazioni di registrazione, autenticazione, autorizzazione e aggiornamento del profilo. 

### Funzionalità di **PositionService**:
- **Registrazione Utente**: Consente la registrazione di nuovi utenti (camionisti), con l'invio di un'email di conferma.
- **Login**: Fornisce un endpoint di login per consentire agli utenti di accedere al sistema.
- **Aggiornamento Profilo**: Gli utenti possono aggiornare i propri dettagli personali come nome, cognome, numero di telefono e genere..
- **Cambio password**: Se l'utente ha dimenticato la propria password, può chiederne il cambio: inserendo l'email verrà inviato un codice per il reset della password. Se invece l'utente vuole semplicemente cambiare password può farlo accedendo all'interno del suo profilo e modificando la password, inserendo prima la vecchia.
  
Il **PositionService** è un componente fondamentale per il funzionamento del sistema, in quanto permette di creare il profilo utente del camionista, che si rende disponibile a ricevere delle tratte.


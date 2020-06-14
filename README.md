Projekat iz predmeta MRS i ISA.

Clanovi tima:
  Jelena Cupać sw13-2017
  Aleksa Goljović sw14-2017
  Milan Marinković sw57-2017

Tehnologije:
	- Java Spring
	- Vue.js
	- Vuetify
	- PostgreSQL
	
Pre pokretanja potrebno je imati instalirano:
	- Java SDK
	- Apache Maven
	- PostgreSQL
		
Postupak pokretanja aplikacije:
	1. način:
		- Skinuti aplikaciju i pozicionirati se u direktorijum MRSISA_TIM9
		- U tom direktorijumu u konzoli izvršiti naredbu: mvn spring-boot:run
	
	2. način:
		- Importovati projekat u Eclipse IDE
		- Pokrenuti aplikaciju komandom Run as -> Spring Boot Application ili Run as -> Java Application
	*Napomena: za prva dva načina pokretanja potrebno je napraviti bazu podataka pod nazivom KlinikaDB na
	svom postgres nalogu, zatim podesiti šifru i username u application.properties. Za url baze potrebno je otkomentarisati
	liniju broj 12 u application.properties, a zakomentarisati liniju broj 13.
	3. načina:
		- Kako je urađen deployment, aplikaciju možete pokrenuti unošenjem sledećeg linka u pretraživač browser-a
		- link: https://mrsisa09.herokuapp.com/
			
*Napomena: Analiza Sonara ne prolazi jer Sonar zahteva da 80% koda bude pokriveno testovima.
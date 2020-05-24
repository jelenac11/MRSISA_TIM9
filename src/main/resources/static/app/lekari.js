Vue.component("lekari", {
	data : function() {
		return {
			lekari: [],
			search: "",
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="naviga tab-content">
			<div class="input-group">
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="search"  placeholder="Ime i prezime..."/>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="10%">Ime</th>
				      	<th scope="col" width="10%">Prezime</th>
				      	<th scope="col" width="20%">Email</th>
				      	<th scope="col" width="10%">Adresa</th>
				      	<th scope="col" width="10%">Grad</th>
				      	<th scope="col" width="10%">Država</th>
				      	<th scope="col" width="10%">Broj telefona</th>
				      	<th scope="col" width="10%">Radno vreme</th>
				      	<th scope="col" width="10%"></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="lekar in filtriraniLekari" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="10%">{{ lekar.ime }}</td>
				      	<td width="10%">{{ lekar.prezime }}</td>
				      	<td width="20%">{{ lekar.email }}</td>
				      	<td width="10%">{{ lekar.adresa }}</td>
				      	<td width="10%">{{ lekar.grad }}</td>
				      	<td width="10%">{{ lekar.drzava }}</td>
				      	<td width="10%">{{ lekar.brojTelefona }}</td>
				      	<td width="10%">{{ radnoVreme(lekar.pocetakRadnogVremena, lekar.krajRadnogVremena) }}</td>
			    		<td width="10%"><button class="btn btn-danger btn-sm" v-on:click="obrisiLekara(lekar)" id="brisanjeTipa">Ukloni</button></td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeLekara' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeLekara">Dodaj novog Lekara</router-link>
		</div>
	</div>
	`
	,
	methods : {
		radnoVreme : function (pocetak, kraj) {
		    minutesPoc = Math.floor((pocetak / (1000 * 60)) % 60),
		    hoursPoc = Math.floor((pocetak / (1000 * 60 * 60)) % 24);
	
			hoursPoc = (hoursPoc < 10) ? "0" + hoursPoc : hoursPoc;
			minutesPoc = (minutesPoc < 10) ? "0" + minutesPoc : minutesPoc;

			minutesKraj = Math.floor((kraj / (1000 * 60)) % 60),
		    hoursKraj = Math.floor((kraj / (1000 * 60 * 60)) % 24);
	
			hoursKraj = (hoursKraj < 10) ? "0" + hoursKraj : hoursKraj;
			minutesKraj = (minutesKraj < 10) ? "0" + minutesKraj : minutesKraj;

			return hoursPoc + ":" + minutesPoc + " - " + hoursKraj + ":" + minutesKraj;
		},
		obrisiLekara: function(lekar){
			var hoceDaBrise = confirm("Da li ste sigurni da želite izbrisati lekara "+ lekar.ime+" "+lekar.prezime +"?");
			if (hoceDaBrise == true) {
				axios
				.post('lekari/izbrisiLekara', lekar, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					if(response.data==true){
						toast("Uspešno izbrisan lekar "+ lekar.ime+" "+lekar.prezime +".");
						axios
				        .get('lekari/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
				        .then(response => (this.lekari = response.data))
				        .catch(function (error) { console.log(error); });
						
					}
					else{
						toast("Nije moguće izbrisati izabranog lekara.");
						
					}
				})
				.catch(function (error) { console.log(error); });
			}
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('lekari/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.lekari = response.data))
        .catch(function (error) { console.log(error); });
	},
	computed : {
		filtriraniLekari : function() {
			return this.lekari.filter(lekar => {
				return lekar.ime.toLowerCase().includes(this.search.toLowerCase().trim()) || lekar.prezime.toLowerCase().includes(this.search.toLowerCase().trim());
			})
		}
	}
});
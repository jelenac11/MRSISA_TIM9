Vue.component('pretraga-lekara', {
	data : function() {
		return {
			ulogovan : {},
			klinika: {},
			lekari : [],
			token: "",
			ocenaFilter : false,
			ocenaDonja: 0,
			ocenaGornja: 0,
			imePrezime: "",
			tip: "",
			vreme: "",
			id: 0,
			termini : [],
			predefinisani: [],
			lekar: 0,
			potvrda: {},
		} 
	},
	template: `
		<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga tab-pane fade show active" id="pills-pk" role="tabpanel" >
			<div class="kartica mt-2 mx-2" style="width: 98.7%;">
			  <div class="kartica-body">
			    <h4 class="kartica-title">{{ klinika.naziv + ", " + klinika.ocena }}</h4>
			    <h6 class="kartica-subtitle mb-2 text-muted">{{ klinika.lokacija }}</h6>
			    <p class="kartica-text">{{ klinika.opis }}</p>
			  </div>
			</div>
			<div class="input-group">
				<span class="input-group-btn">
			    	<a class="btn btn-primary my-2 mx-2" data-toggle="collapse" href="#predefinisaniTermini" role="button">
				    	Brzo zakazivanje
				  	</a>
			  	</span>
			  	<span class="input-group-btn">
			    	<a class="btn btn-info my-2 mr-2" data-toggle="collapse" href="#filteriLekari" role="button">
				    	Prikaži filtere
				  	</a>
			  	</span>
				<input type="search" class="form-control col-4 my-2 mr-2" style="height:40px" v-model="imePrezime" placeholder="Ime i prezime..."/>
				<span class="input-group-btn">
					<button class="btn btn-danger mt-2 mr-2" v-on:click="ocisti">
				    	Očisti
				  	</button>
				</span>
			</div>
			<div class="collapse" id="predefinisaniTermini">
				<div class="kartica kartica-body m-2">
		    		<form>
					  	<div class="form-row"> 
					  		<table class="table table-hover">
							  	<thead class="thead-light">
							    	<tr>
								      	<th scope="col" width="20%">Vreme</th>
								      	<th scope="col" width="7.5%">Sala</th>
								      	<th scope="col" width="25%">Lekar</th>
								      	<th scope="col" width="20%">Tip pregleda</th>
								      	<th scope="col" width="10%">Cena</th>
								      	<th scope="col" width="7.5%">Popust</th>
								      	<th scope="col" width="10%"></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  		<tr v-for="(predef,indeks) in predefinisani">
								      	<td width="20%">{{ urediDatum(predef.datum) }}</td>
								      	<td width="7.5%">{{ predef.sala}}</td>
								      	<td width="25%">{{ predef.lekar.ime }} {{predef.lekar.prezime}}</td>
								      	<td width="20%">{{ predef.tip.naziv }}</td>
								      	<td width="10%">{{ predef.cena }}</td>
								      	<td width="7.5%">{{ predef.popust + "%" }}</td>
								      	<td width="10%"><button type="button" class="btn btn-primary" v-on:click="zakazivanjePredefinisanog(indeks)">Zakaži</button></td>
							    	</tr>
							  	</tbody>
							</table>
						</div>
					</form>
		  		</div>
			</div>
			<div class="collapse" id="filteriLekari">
		  		<div class="kartica kartica-body m-2">
		    		<form>
					  	<div class="form-row mb-4"> 
					  		<div class="mt-5 mr-3 custom-control custom-checkbox">
					  			<input type="checkbox" class="custom-control-input" id="ocenaFilter" v-model="ocenaFilter">
					  			<label class="custom-control-label" for="ocenaFilter"><font size="4">Ocena</font></label>
							</div>
					  		<div class="col-2 mt-1 mx-3">
					    	 	<label for="odocena">Od</label>
								<input type="number" v-model="ocenaDonja" class="form-control" id="odocena" placeholder="Od">
							</div>
							<div class="col-2 mt-1 mx-3">
					    	 	<label for="doocena">Do</label>
								<input type="number" v-model="ocenaGornja" class="form-control" id="doocena" placeholder="Do">
							</div>
						</div>
					</form>
		  		</div>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="40%">Ime</th>
				      	<th scope="col" width="40%">Prezime</th>
				      	<th scope="col" width="20%">Ocena</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="le in filtriraniLekari" v-on:click="izaberiLekara(le)" data-toggle="modal" data-target="#prikazSlobodnihTermina">
				      	<td width="30%">{{ le.ime }}</td>
				      	<td width="30%">{{ le.prezime }}</td>
				      	<td width="20%">{{ le.prosecnaOcena }}</td>
			    	</tr>
			  	</tbody>
			</table>
		</div>
		
		<div class="modal fade" id="prikazSlobodnihTermina" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<h5 class="modal-title">Slobodni termini</h5>
		        		<button type="button" class="close" id = "iksic" data-dismiss="modal"><span>&times;</span></button>
		      		</div>
		      		<div class="modal-body">
			        	<table class="table table-hover">
						  	<thead class="thead-light">
						    	<tr>
							      	<th scope="col" width="70%">Termin</th>
							      	<th scope="col" width="30%"></th>
						    	</tr>
						  	</thead>
						  	<tbody>
						  		<tr v-for="te in termini">
							      	<td width="70%">{{ urediDatum(te) }}</td>
							      	<td width="20%"><button type="button" class="btn btn-primary" v-on:click="zakazivanjeSlobodnog(te)">Zakaži</button></td>
						    	</tr>
						  	</tbody>
						</table>
			      	</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
		      		</div>
		    	</div>
			</div>
		</div>
		
	</div>
	`
	, 
	methods : {
		zadovoljavaOcenu : function (lekar) {
			if (this.ocenaFilter) {
	    		if (lekar.prosecnaOcena >= this.ocenaDonja && lekar.prosecnaOcena <= this.ocenaGornja) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return true;
	    	}
		},
		izaberiLekara: function(lekar){
			this.lekar = lekar.id;
			axios
			.put("/lekari/vratiSlobodneTermine", {id: lekar.id, datum: this.vreme, tipPregleda: this.tip},  { headers: { Authorization: 'Bearer ' + this.token }})
			 .then(response => (this.termini = response.data))
		     .catch(function (error) { console.log(error); });
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		zakazivanjePredefinisanog : function(indeks) {
		    this.predefinisani[indeks].pacijent = this.ulogovan;
			this.predefinisani[indeks].lokacija = this.klinika;
			axios
			.put("/pregledi/zakaziPredefinisani", this.predefinisani[indeks], { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => (this.dobaviPredefinisane()))
		    .catch(function (error) { console.log(error); });
		},
		dobaviPredefinisane: function(){
			axios
	        .put('/pregledi/ucitajPredefinisanePreglede', { id: this.id, datum: this.vreme, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => (this.predefinisani = response.data))
	        .catch(function (error) { console.log(error); });
		},
		zakazivanjeSlobodnog: function(termin) {
			axios
			.put("/pregledi/zakaziTermin", { id: this.lekar, datum: termin, tipPregleda: this.tip, pacijent: this.ulogovan.id, klinika : this.klinika.id}, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {this.potvrda = response.data; $('#iksic').click(); this.$router.push({ name: 'potvrdaZakazivanja', params: {pregled : this.potvrda, zaposleni: false}});})
		    .catch(function (error) { console.log(error); });
		},
		ocisti : function () {
			this.imePrezime = "";
			this.ocenaFilter = false;
			this.ocenaDonja = 0;
			this.ocenaGornja = 0;
		},
	},
	computed: {
	    filtriraniLekari : function () {
	    	return this.lekari.filter(lekar => {
				return (lekar.ime.toLowerCase().includes(this.imePrezime.toLowerCase().trim()) || lekar.prezime.toLowerCase().includes(this.imePrezime.toLowerCase().trim())) &&
				this.zadovoljavaOcenu(lekar);
	    	})
	    },
	},
	created() {
		this.token = localStorage.getItem("token");
		this.tip = this.$route.params.tip;
		this.vreme = this.$route.params.vreme;
		this.id = this.$route.params.id;
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data;})
        .catch(function (error) { console.log(error); });
		axios
		.get('/klinike/' +  this.id, { headers: { Authorization: 'Bearer ' + this.token }})
		.then(response => (this.klinika = response.data))
        .catch(function (error) { console.log(error); });
		axios
        .put('/klinike/ucitajSlobodneLekare', { id: this.id, datum: this.vreme, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.lekari = response.data))
        .catch(function (error) { console.log(error); });
		this.dobaviPredefinisane();
	}
})
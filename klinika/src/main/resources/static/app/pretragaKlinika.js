Vue.component("pretraga-klinika", {
	data : function() {
		return {
			klinike : [],
			token: "",
			pretraga: "",
			ocenaFilter : false,
			cenaFilter: false,
			ocenaDonja: 0,
			ocenaGornja: 0,
			cenaDonja: 0,
			cenaGornja: 0,
			naziv: "",
			lokacija: "",
			datum : null,
			tip: "",
			tipovi: [],
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="tab-pane fade show active" id="pills-pk" role="tabpanel" >
			<div class="input-group">
			  	<span class="input-group-btn">
			    	<a class="btn btn-secondary m-2" data-toggle="collapse" href="#filteriKlinike" role="button">
				    	Prikaži filtere
				  	</a>
			  	</span>
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="naziv"  placeholder="Naziv..."/>
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="lokacija"  placeholder="Lokacija..."/>
				<input type="date" v-model="datum" id="datumID" placeholder="Datum..." class="form-control col-4 ml-auto m-2">
				<div class="form-control col-4 ml-auto m-2">
						<select class="custom-select mt-0" v-model="tip" id="tip">
							<option value="" disabled selected hidden>Tipovi pregleda...</option>
					    	<option v-for="t in tipovi" :value="t.naziv">
								{{ t.naziv }}
					    	</option>
					  	</select>
					  	<div class="invalid-feedback" id="dodavanjeInvalid">Niste izabrali tip pregleda.</div>
				</div>
			</div>
			<div class="collapse" id="filteriKlinike">
		  		<div class="card card-body m-2">
		    		<form>
					  	<div class="form-row mb-4"> 
					  		<div class="mt-5 mr-3 custom-control custom-checkbox">
					  			<input type="checkbox" class="custom-control-input" id="ocenaFilter" v-model="ocenaFilter">
					  			<label class="custom-control-label" for="ocenaFilter"><font size="4">Ocena</font></label>
							</div>
					  		<div class="col-2 mt-1">
					    	 	<label for="odocena">Od</label>
								<input type="number" v-model="ocenaDonja" class="form-control" id="odocena" placeholder="Od">
							</div>
							<div class="col-2 mt-1">
					    	 	<label for="doocena">Do</label>
								<input type="number" v-model="ocenaGornja" class="form-control" id="doocena" placeholder="Do">
							</div>
							<div class="mt-5 mr-3 custom-control custom-checkbox">
					  			<input type="checkbox" class="custom-control-input" id="cena" v-model="cenaFilter">
					  			<label class="custom-control-label" for="cena"><font size="4">Cena</font></label>
							</div>
					  		<div class="col-2 mt-1">
					    	 	<label for="odcena">Od</label>
								<input type="number" v-model="cenaDonja" class="form-control" id="odcena" placeholder="Od">
							</div>
							<div class="col-2 mt-1">
					    	 	<label for="docena">Do</label>
								<input type="number" v-model="cenaGornja" class="form-control" id="docena" placeholder="Do">
							</div>
						</div>
					</form>
		  		</div>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="30%">Naziv</th>
				      	<th scope="col" width="30%">Lokacija</th>
				      	<th scope="col" width="20%">Ocena</th>
				      	<th scope="col" width="20%">Cena pregleda</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="kl in filtriraneKlinike">
				      	<td width="30%">{{ kl.naziv }}</td>
				      	<td width="30%">{{ kl.lokacija }}</td>
				      	<td width="20%">{{ kl.ocena }}</td>
				      	<td width="20%">{{ kl.cenaPregleda }}</td>
			    	</tr>
			  	</tbody>
			</table>
		</div>
	</div>
	`
	,
	
	methods : {
		zadovoljavaOcenu : function (klinika) {
	    	if (this.ocenaFilter) {
	    		if (klinika.ocena >= this.ocenaDonja && klinika.ocena <= this.ocenaGornja) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return true;
	    	}
		},
		zadovoljavaCenu : function (klinika) {
	    	if (this.cenaFilter) {
	    		if (klinika.cenaPregleda >= this.cenaDonja && klinika.cenaPregleda <= this.cenaGornja) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return true;
	    	}
		},
		zadovoljavaDatumITip : function (klinika) {
	    	datum = new Date(this.datum+"");
	    	timestamp = datum.getTime() - 7200000;
	    	if (this.tip != "") {
	    		axios
	    		.put('/klinike/zadovoljavaTip', { id: klinika.id, datum: timestamp, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }})
	    		.then(response => {klinika.zadovoljava = response.data;})
	            .catch(function (error) { console.log(error); })
	    	} else {
	    		klinika.zadovoljava = true;
	    	}
		},
		
		dobaviCenu : function(klinika, indeks) {
			datum = new Date(this.datum+"");
			timestamp = datum.getTime();
			if (this.tip != "" && this.datum != null) {
	    		axios
	    		.put('/stavkeCenovnika/dobaviCenuPregleda', { id: klinika.id, datum: timestamp, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }})
	    		.then(response => {this.$set(this.klinike[indeks], 'cenaPregleda', response.data); console.log("cena za " + this.tip + " za kliniku " + klinika.id + " je " + klinika.cenaPregleda);})
	            .catch(function (error) { console.log(error); })
	    	} else {
	    		this.$set(this.klinike[indeks], 'cenaPregleda', 0);
	    	}
		}
	},
	
	computed: {
	    filtriraneKlinike : function () {
	    	return this.klinike.filter(klinika => {
	    		this.dobaviCenu(klinika, this.klinike.indexOf(klinika));
	    		if (this.tip != "" && this.datum != null) {
					this.zadovoljavaDatumITip(klinika);
					return klinika.naziv.toLowerCase().includes(this.naziv.toLowerCase()) && klinika.lokacija.toLowerCase().includes(this.lokacija.toLowerCase()) &&
					this.zadovoljavaOcenu(klinika) && this.zadovoljavaCenu(klinika) && klinika.zadovoljava;
	    		} else {
	    			return true;
	    		}
	    	})
	    },
	},
	
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('/klinike/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.klinike = response.data))
        .catch(function (error) { console.log(error); });
		axios
        .get('/tipoviPregleda/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.tipovi = response.data))
        .catch(function (error) { console.log(error); });
	}
});
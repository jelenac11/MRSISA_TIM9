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
			milisekunde: 0,
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga tab-pane fade show active" id="pills-pk" role="tabpanel" >
			<p class="m-1 ml-3 mt-2 font-weight-normal">*Za pretragu klinika je neophodno uneti tip pregleda i datum.</p>
			<div class="input-group">
			  	<span class="input-group-btn">
			    	<a class="btn btn-info m-2" data-toggle="collapse" href="#filteriKlinike" role="button">
				    	Prikaži filtere
				  	</a>
			  	</span>
				<input type="search" class="form-control col-4 ml-auto my-2 mr-2" style="height:40px" v-model="naziv"  placeholder="Naziv..."/>
				<input type="search" class="form-control col-4 ml-auto my-2 mr-2" style="height:40px" v-model="lokacija"  placeholder="Lokacija..."/>
				<input type="date" v-model="datum" id="datumID" placeholder="Datum..." style="height:40px" class="form-control col-4 ml-auto my-2 mr-2">
				<select class="custom-select my-2 mr-2" v-model="tip" style="height:40px" id="tip">
					<option value="" disabled selected hidden>Tipovi pregleda...</option>
			    	<option v-for="t in tipovi" :value="t.naziv">
						{{ t.naziv }}
			    	</option>
			  	</select>
				<span class="input-group-btn">
					<button class="btn btn-danger mt-2 mr-2" v-on:click="ocisti">
				    	Očisti
				  	</button>
				</span>
			</div>
			<div class="collapse" id="filteriKlinike">
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
							<div class="mt-5 ml-4 mr-3 custom-control custom-checkbox">
					  			<input type="checkbox" class="custom-control-input" id="cena" v-model="cenaFilter">
					  			<label class="custom-control-label" for="cena"><font size="4">Cena</font></label>
							</div>
					  		<div class="col-2 mt-1 mx-3">
					    	 	<label for="odcena">Od</label>
								<input type="number" v-model="cenaDonja" class="form-control" id="odcena" placeholder="Od">
							</div>
							<div class="col-2 mt-1 mx-3">
					    	 	<label for="docena">Do</label>
								<input type="number" v-model="cenaGornja" class="form-control" id="docena" placeholder="Do">
							</div>
						</div>
					</form>
		  		</div>
			</div>
			<table class="table table-hover table-striped sortable" id="tabela">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="30%"><button class="btn">Naziv</button></th>
				      	<th scope="col" width="30%"><button class="btn">Lokacija</button></th>
				      	<th scope="col" width="20%"><button class="btn">Ocena</button></th>
				      	<th data-defaultsort='disabled' id="cenaKolona" scope="col" width="20%"><button class="btn">Cena pregleda</button></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="kl in filtriraneKlinike" v-on:click="izaberiKliniku(kl)">
				      	<td width="30%">{{ kl.naziv }}</td>
				      	<td width="30%">{{ kl.lokacija }}</td>
				      	<td width="20%">{{ kl.ocena }}</td>
				      	<td id="cenaKolonica" width="20%">{{ prikazi(kl.cenaPregleda) }}</td>
			    	</tr>
			  	</tbody>
			</table>
		</div>
	</div>
	`
	,
	
	methods : {
		izaberiKliniku : function(klinika) {
			if (this.tip != "" && this.datum != null) {
				datum = new Date(this.datum+"");
		    	timestamp = datum.getTime() - 7200000;
		    	this.$router.push({ name: 'pretragaLekara', params: {id: klinika.id, vreme : timestamp, tip : this.tip }})
			} else {
				this.$router.push({ name: 'zaposleni', params: {id: klinika.id }})
			}
		},
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
	    		.then(response => {klinika.zadovoljava = response.data})
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
	    		.then(response => {this.$set(this.klinike[indeks], 'cenaPregleda', response.data);})
	            .catch(function (error) { console.log(error); })
	    	} else {
	    		this.$set(this.klinike[indeks], 'cenaPregleda', 0);
	    	}
		},
		prikazi : function(cena) {
			if (this.tip != "" && this.datum != null) {
				return cena;
			} else {
				return "";
			}
		},
		ocisti : function () {
			this.pretraga = "";
			this.ocenaFilter = false;
			this.cenaFilter = false;
			this.ocenaDonja = 0;
			this.ocenaGornja = 0;
			this.cenaDonja = 0;
			this.cenaGornja = 0;
			this.naziv = "";
			this.lokacija = "";
			this.datum = null;
			this.tip = "";
		},
	},
	
	computed: {
	    filtriraneKlinike : function () {
	    	return this.klinike.filter(klinika => {
	    		this.dobaviCenu(klinika, this.klinike.indexOf(klinika));
	    		if (this.tip != "" && this.datum != null) {
	    			$("#cenaKolona").html("<button class='btn'>Cena pregleda</button>");
					this.zadovoljavaDatumITip(klinika);
					return klinika.naziv.toLowerCase().includes(this.naziv.toLowerCase().trim()) && klinika.lokacija.toLowerCase().includes(this.lokacija.toLowerCase().trim()) &&
					this.zadovoljavaOcenu(klinika) && this.zadovoljavaCenu(klinika) && klinika.zadovoljava;
	    		} else {
	    			$("#cenaKolona").text("");
	    			return true;
	    		}
	    	})
	    },
	},
	
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('/klinike/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.klinike = response.data))
        .catch(function (error) { console.log(error); });
		axios
        .get('/tipoviPregleda/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.tipovi = response.data))
        .catch(function (error) { console.log(error); });
	},
	mounted () {
		$.bootstrapSortable({ applyLast: true });
		$("#tabela").on('sorted', function () { 
			if (!this.sortirano) {
				this.sortirano = true;
				$.bootstrapSortable({ applyLast: true });
			} 
		});
	}
});
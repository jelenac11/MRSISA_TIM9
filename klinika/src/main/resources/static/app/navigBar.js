Vue.component('navig-bar', {
	data : function() {
		return {
			ulogovan: {},
			uloga: "",
			klinika:{}
		} 
	},
	props: ['token'],
	name: 'navig-bar',
	template: `
		<div>
			<nav id="sidebar" class="px-3 pt-3 pb-5">
		        <ul class="nav flex-column">
			        <li class="nav-item mb-4">
			            <h4><a class="nav-link font-weight-bold text-white" href="#">Klinika</a></h4>
			        </li>
			          
					<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
						<router-link :to="{ name: 'pretragaKlinika' }" data-toggle="pill" class="nav-link">Klinike</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
						<router-link :to="{ name: 'istorijaPregleda' }" data-toggle="pill" class="nav-link">Istorija pregleda</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_PACIJENT'" class="nav-item">
						<router-link :to="{ name: 'zdravstveniKarton' }" data-toggle="pill" class="nav-link">Zdravstveni karton</router-link>
					</li>
					
					<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
						<router-link :to="{ name: 'pacijenti' }" data-toggle="pill" class="nav-link">Pacijenti</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
						<router-link :to="{ name: 'zapocniPregled' }" data-toggle="pill" class="nav-link">Započni pregled</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
						<router-link :to="{ name: 'radniKalendar' }" data-toggle="pill" class="nav-link">Radni kalendar</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
						<router-link :to="{ name: 'zahtevOdsustvo' }" data-toggle="pill" class="nav-link">Odsustvo</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_LEKAR'" class="nav-item">
						<router-link :to="{ name: 'zakazivanjeLekar' }" data-toggle="pill" class="nav-link">Zakaži pregled</router-link>
					</li>
					
					<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
						<router-link :to="{ name: 'pacijenti' }" data-toggle="pill" class="nav-link">Pacijenti</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
						<router-link :to="{ name: 'radniKalendar' }" data-toggle="pill" class="nav-link">Radni kalendar</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
						<router-link :to="{ name: 'zahtevOdsustvo' }" data-toggle="pill" class="nav-link">Odsustvo</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_MED_SESTRA'" class="nav-item">
						<router-link :to="{ name: 'overavanjeRecepata' }" data-toggle="pill" class="nav-link">Overi recepte</router-link>
					</li>
					
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'zahteviRegistracija' }" data-toggle="pill" class="nav-link">Zahtevi za registraciju</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'sifrarnikLekova' }" data-toggle="pill" class="nav-link">Šifrarnik lekova</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'sifrarnikDijagnoza' }" data-toggle="pill" class="nav-link">Šifrarnik dijagnoza</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'klinikeAdmin' }" data-toggle="pill" class="nav-link">Klinike</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'adminiKlinike' }" data-toggle="pill" class="nav-link">Admini klinike</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINICKOG_CENTRA'" class="nav-item">
						<router-link :to="{ name: 'adminiCentra' }" data-toggle="pill" class="nav-link">Admini centra</router-link>
					</li>
					
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'klinika' }" data-toggle="pill" class="nav-link">Klinika</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'cenovnik' }" data-toggle="pill" class="nav-link">Cenovnik</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'zakazaniPregledi' }" data-toggle="pill" class="nav-link">Termini</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'poslovanje' }" data-toggle="pill" class="nav-link">Poslovanje</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'sale' }" data-toggle="pill" class="nav-link">Sale</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'tipoviPregleda' }" data-toggle="pill" class="nav-link">Tipovi pregleda</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'lekari' }" data-toggle="pill" class="nav-link">Lekari</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'medSestre' }" data-toggle="pill" class="nav-link">Medicinske sestre</router-link>
					</li>
					<li v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="nav-item">
						<router-link :to="{ name: 'zahtjevi' }" data-toggle="pill" class="nav-link">Zahtevi odsustvo</router-link>
					</li>
		        </ul>
		      </nav>
			
			<div class="navig">
				<nav class="navigbar navbar navbar-expand-md navbar-dark bg-dark">
					<button type="button" v-on:click="togl" id="sidebarCollapse" class="btn">
			            <img src="meni.png" width="30" height="30" alt="" class="d-inline-block align-top mr-2 icon-toggle">
			        </button>
			    
				    <div class="navbar-collapse collapse w-100 order-1 dual-collapse2">
				        <ul class="navbar-nav ml-auto">
				            <li class="nav-item dropdown">
				                <a class="navbar-brand dropdown-toggle" href="#" data-toggle="dropdown">
								    <img src="profilepic2.png" width="30" height="30" class="d-inline-block align-top mr-2" alt="">
								    {{ this.ulogovan.ime + " " + this.ulogovan.prezime }}
							  	</a>
							  	<div class="dropdown-menu">
							  		<a class="dropdown-item" data-toggle="modal" data-target="#profilModal" href="">Pregled profila</a>
							  		<a class="dropdown-item" v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" data-toggle="modal" data-target="#profilKlinikeModal" href='#' v-on:click="prikaziPodatkeKlinike">Pregled profila klinike</a>
						        	<a class="dropdown-item" v-on:click="odjaviSe" href="#">Odjavi se</a>
						        </div>
				            </li>
				        </ul>
				    </div>
				</nav>
			</div>
			
			<div class="modal fade" id="profilModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
			    	<div class="modal-content">
						<div class="modal-header">
			        		<h5 class="modal-title" id="exampleModalLabel">Pregled profila</h5>
			        		<button type="button" class="close" data-dismiss="modal">
			          			<span>&times;</span>
			        		</button>
			      		</div>
			      		<div class="modal-body">
			        		<ul class="list-group">
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Email adresa:</h6>
								  		<p class="mb-0">{{ this.ulogovan.email }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Ime:</h6>
								  		<p class="mb-0">{{ this.ulogovan.ime }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Prezime:</h6>
							  			<p class="mb-0">{{ this.ulogovan.prezime }}</p>
							  		</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Adresa:</h6>
								  		<p class="mb-0">{{ this.ulogovan.adresa }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Grad:</h6>
							  			<p class="mb-0">{{ this.ulogovan.grad }}, {{this.ulogovan.drzava}}</p>
							  		</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Broj telefona:</h6>
							  			<p class="mb-0">{{ this.ulogovan.brojTelefona }}</p>
							  		</div>
							  	</li>
							</ul>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			        		<router-link :to="{ name: 'izmenaProfila' }" class="btn btn-primary" data-dismiss="modal">Izmena podataka</router-link>
			      		</div>
			    	</div>
				</div>
			</div>
			
			<div class="modal fade" id="profilKlinikeModal" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
			    	<div class="modal-content">
						<div class="modal-header">
			        		<h5 class="modal-title" id="exampleModalLabel">Pregled profila klinike</h5>
			        		<button type="button" class="close" data-dismiss="modal">
			          			<span>&times;</span>
			        		</button>
			      		</div>
			      		<div class="modal-body">
			        		<ul class="list-group">
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Naziv:</h6>
								  		<p class="mb-0">{{ this.klinika.naziv }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
								  		<h6>Lokacija:</h6>
								  		<p class="mb-0">{{ this.klinika.lokacija }}</p>
								  	</div>
							  	</li>
							  	<li class="list-group-item">
							  		<div class="d-flex w-20 justify-content-between">
							  			<h6>Opis:</h6>
							  			<p class="mb-0">{{ this.klinika.opis }}</p>
							  		</div>
							  	</li>
							</ul>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			        		<router-link :to="{ name: 'izmenaProfilaKlinike', params: {klinikaParam:this.klinika }}" class="btn btn-primary" data-dismiss="modal">Izmena podataka</router-link>
			      		</div>
			    	</div>
				</div>
			</div>
		</div>
	`
	,
	methods : {
		odjaviSe : function () {
			localStorage.removeItem("token");
			this.$router.replace({ path: '/' });
		},
		prikaziPodatkeKlinike: function(){
			axios
			.get("adminiKlinike/ucitajKlinikuPoIDAdmina/"+this.ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.klinika=response.data;
			})
			.catch(function (error) { console.log(error); });
		},
		togl : function () {
			$("#sidebar").toggleClass("active");
			$(".naviga").toggleClass("activenavig");
		}
	},
	created() {
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
    		.put('/korisnici/dobaviUlogu', this.ulogovan, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.uloga = response.data; })
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
})
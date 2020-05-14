Vue.component("zahtevi-registracija",{
	data : function(){
		return {
            zahtevi : null,
            indeks: null,
            ulogovan: {},
            submitovano: false,
            token: "",
		}
	},
	
    template: `
	<div>
	    <navig-bar v-bind:token="this.token"></navig-bar>
	    
        <table class="naviga table table-hover table-striped">
            <thead class="thead-light">
                <tr>
                    <th scope="col">Email</th>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Adresa</th>
                    <th scope="col">Grad</th>
                    <th scope="col">Drzava</th>
                    <th scope="col">JBO</th>
                    <th scope="col" colspan="2">Akcija</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(zahtev, indeks) in zahtevi">
                    <td>{{zahtev.email}}</td>
                    <td>{{zahtev.ime}}</td>
                    <td>{{zahtev.prezime}}</td>
                    <td>{{zahtev.adresa}}</td>
                    <td>{{zahtev.grad}}</td>
                    <td>{{zahtev.drzava}}</td>
                    <td>{{zahtev.jbo}}</td>
                    <td><button type="button" class="btn btn-success" v-on:click="prihvatanjeZahteva(indeks)">Prihvati zahtev</button></td>
                    <td><button type="button" class="btn btn-danger" data-target="#obrazlozenjeModal" data-toggle="modal" v-on:click="zabeleziIndeks(indeks)">Odbij zahtev</button></td>
                </tr>
            </tbody>
        </table>
        
        <div class="modal fade" id="obrazlozenjeModal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<form class="needs-validation mb-4" id="forma-obrazlozenje-odbijanja" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="odbijanjeZahteva">
			    	<div class="modal-content">
						<div class="modal-header">
			        		<h5 class="modal-title" id="exampleModalLabel">Obrazloženje odbijanja zahteva</h5>
			        		<button type="button" class="close" data-dismiss="modal">
			          			<span>&times;</span>
			        		</button>
			      		</div>
			      		<div class="modal-body">
		                  	<input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazloženje odbijanja zahteva" required>
			      			<div class="invalid-feedback" id="dodavanjeInvalid">Unesite razlog odbijanja zahteva za odsustvom.</div>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			        		<button class="btn btn-primary" type="submit">Odbij zahtev</button>
			      		</div>
			    	</div>
			    </form>
			</div>
		</div>
    </div>
	`
		,
	methods: 
	{
		prihvatanjeZahteva: function(indeks) {
			this.zahtevi[indeks].aktiviran = true;
            this.zahtevi[indeks].obrazlozenje = "";
            this.updateZahtev(indeks);
        },
        zabeleziIndeks: function(indeks) {
            this.indeks = indeks;
            $("#obrazlozenje").val("");
            this.submitovano = false;
        },
		odbijanjeZahteva: function() {
			this.submitovano = true;
			if (document.getElementById('forma-obrazlozenje-odbijanja').checkValidity() === true){
				this.zahtevi[this.indeks].aktiviran = false;
	            this.zahtevi[this.indeks].obrazlozenje = $("#obrazlozenje").val();
	            jQuery.noConflict();
				$('#obrazlozenjeModal').modal('hide');
	            this.updateZahtev(this.indeks);
	            this.submitovano = false;
			}
        },
        updateZahtev: function(indeks) {
            axios
            .put('/auth/updateRegZahtev', this.zahtevi[indeks], { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.dobaviZahteve() })
            .catch(function (error) { console.log(error); });
        },
        dobaviZahteve: function() {
            axios
            .get('/pacijenti/vratiRegZahteve', { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.zahtevi = response.data; })
            .catch(function (error) { console.log(error); });
        },
	},
	created () {
		this.token = localStorage.getItem("token");
        this.dobaviZahteve();
	}
});
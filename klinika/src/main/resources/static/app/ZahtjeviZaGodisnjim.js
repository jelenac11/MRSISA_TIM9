Vue.component("zahtjevGodisnji",{
	data : function(){
		return {
            zahtjevi: null,
            indeks: null,
            ulogovan: {},
            submitovano: false,
            token: "",
		}
	},
    template: `
	<div>
    	<navig-bar v-bind:token="this.token"></navig-bar>
    
        <table class="table table-hover table-striped">
            <thead class="thead-light">
                <tr>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Početak odsustva</th>
                    <th scope="col">Kraj odsustva</th>
                    <th scope="col" colspan="2">Akcija</th>
                </tr>

            </thead>
            <tbody>
                <tr v-for="(zahtjev,indeks) in zahtjevi">
                    <td>{{zahtjev.podnosilac.ime}}</td>
                    <td>{{zahtjev.podnosilac.prezime}}</td>
                    <td>{{urediDatum(zahtjev.pocetak)}}</td>
                    <td>{{urediDatum(zahtjev.kraj)}}</td>
                    <td><button type="button" class="btn btn-success" v-on:click="prihvatanjeZahtjeva(indeks)">Prihvati zahtev</button></td>
                    <td><button type="button" class="btn btn-danger" data-target="#obrazlozenjeModal" data-toggle="modal" v-on:click="zabiljeziIndeks(indeks)">Odbij zahtev</button></td>
                </tr>
            </tbody>
        </table>
        <div class="modal fade" id="obrazlozenjeModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
		<form class="needs-validation mb-4" id="forma-obrazlozenje-odbijanja" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="odbijanjeZahtjeva">
	    	<div class="modal-content">
				<div class="modal-header">
	        		<h5 class="modal-title" id="exampleModalLabel">Obrazlozenje odbijanja zahteva</h5>
	        		<button type="button" class="close" data-dismiss="modal">
	          			<span>&times;</span>
	        		</button>
	      		</div>
	      		<div class="modal-body">
                  	<input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazlozenje odbijanja zahteva" required>
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
	created(){
    	this.token = this.$route.params.korisnikToken;
        this.dobaviZahtjeve()
	},
	methods:
    {
		prihvatanjeZahtjeva: function(indeks){
			this.zahtjevi[indeks].odgovoreno=true
            this.zahtjevi[indeks].odobreno=true
            this.zahtjevi[indeks].obrazlozenje=""
            this.updateZahtjev(indeks)
        },
        zabiljeziIndeks: function(indeks){
            this.indeks=indeks;
            $("#obrazlozenje").val("");
            this.submitovano=false;
        },
		odbijanjeZahtjeva: function(){
			this.submitovano = true;
			if (document.getElementById('forma-obrazlozenje-odbijanja').checkValidity() === true){
				this.zahtjevi[this.indeks].odgovoreno=true
	            this.zahtjevi[this.indeks].odobreno=false
	            this.zahtjevi[this.indeks].obrazlozenje=$("#obrazlozenje").val()
	            jQuery.noConflict();
				$('#obrazlozenjeModal').modal('hide');
	            this.updateZahtjev(this.indeks)
	            this.submitovano=false;
			}
        },
        updateZahtjev: function(indeks){
            axios.put('/odsustva/updateOdsustvo', this.zahtjevi[indeks], { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response=>{ this.dobaviZahtjeve()})
            .catch(function (error) { console.log(error); });
        },
        dobaviZahtjeve: function() {
            axios.get('/odsustva/ucitajSvaNeodgovorenaOdsustva', { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.zahtjevi =response.data; })
            .catch(function (error) { console.log(error); });
        },
        urediDatum:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
        },
	}
});
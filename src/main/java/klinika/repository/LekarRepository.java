package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import klinika.model.Klinika;
import klinika.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1 and k.aktivan = true and (select count(l) from specijalizovan l where l.lekar_id=k.korisnik_id and l.tip_pregleda_id=?2)=1 and "
			+ "(select count(o) from odsustvo o where o.podnosilac_id=k.korisnik_id and (o.pocetak <= ?3 and  ?3 <= o.kraj) and ((o.odgovoreno=true and o.odobreno=true) or o.odgovoreno=false))=0", nativeQuery = true)
	List<Lekar> findBySearchParams(Long id, Long pregledId, Long datum);

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1 and k.aktivan = true", nativeQuery = true)
	List<Lekar> findByKlinikaId(Long id);

	List<Lekar> findAllByKlinikaAndAktivan(Klinika k, boolean akt);

	@Query(value = "select * from korisnici l where l.tip='LE' and l.aktivan = true and l.klinika_id=?1 and ?3 >=l.pocetak_radnog_vremena and ?3<l.kraj_radnog_vremena and (l.pocetak_radnog_vremena - ?3)%3600000 = 0 and (l.kraj_radnog_vremena - 3600000)>=?3 and "
			+ "(select count(s) from specijalizovan s where s.lekar_id=l.korisnik_id and s.tip_pregleda_id=?4)=1 and "
			+ "(select count(pr) from pregled pr where pr.lekar_id=l.korisnik_id and pr.otkazan=false and (pr.vreme >= ?2 and pr.vreme < (?2+3600000)))=0 and "
			+ "(select count(god) from odsustvo god where god.podnosilac_id=l.korisnik_id and (?2 between god.pocetak and god.kraj or (?2+3600000) between god.pocetak and god.kraj) and ((god.odgovoreno=true and god.odobreno=true) or god.odgovoreno=false))=0 and "
			+ "(select count(ope) from operacija ope where ope.otkazana=false and (select op.lekar_id from operisali op where op.lekar_id=l.korisnik_id and op.operacija_id=ope.operacija_id)=l.korisnik_id and ope.vreme >= ?2 and ope.vreme < (?2+3600000))=0", nativeQuery = true)
	List<Lekar> findByIdKlinikaAndVremeAndTipPregleda(long klinikaId, long datumiVreme, long vreme, long tipPregledaId);

	@Query(value = "select * from korisnici l where l.tip='LE' and l.aktivan = true and l.klinika_id=?1 and ?3 >=l.pocetak_radnog_vremena and ?3<l.kraj_radnog_vremena and (l.pocetak_radnog_vremena - ?3)%3600000 = 0 and (l.kraj_radnog_vremena - 3600000)>=?3 and "
			+ "(select count(pr) from pregled pr where pr.lekar_id=l.korisnik_id and pr.otkazan=false and (pr.vreme >= ?2 and pr.vreme < (?2+3600000)))=0 and "
			+ "(select count(god) from odsustvo god where god.podnosilac_id=l.korisnik_id and (?2 between god.pocetak and god.kraj or (?2+3600000) between god.pocetak and god.kraj) and ((god.odgovoreno=true and god.odobreno=true) or god.odgovoreno=false))=0 and "
			+ "(select count(ope) from operacija ope where ope.otkazana=false and (select op.lekar_id from operisali op where op.lekar_id=l.korisnik_id and op.operacija_id=ope.operacija_id)=l.korisnik_id and ope.vreme >= ?2 and ope.vreme < (?2+3600000))=0", nativeQuery = true)
	List<Lekar> findByIdKlinikaAndVreme(long klinikaId, long datumiVreme, long satnica);
	
}

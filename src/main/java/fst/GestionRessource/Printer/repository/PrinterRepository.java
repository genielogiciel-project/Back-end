package fst.GestionRessource.Printer.repository;

import fst.GestionRessource.Printer.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrinterRepository extends JpaRepository<Printer, String> {
}
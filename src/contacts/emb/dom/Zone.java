package contacts.emb.dom;

import java.util.ArrayList;
import java.util.List;

public class Zone {
	// Champs
	
		private int				id;
		
		private String			nom;
		
		
		// Constructeurs
		
		public Zone() {
		}

		public Zone(int id, String nom ) {
			setId(id);
			setNom(nom);
			
		}
		
		
		// Getters & setters

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		
		// hashcode() + equals()
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Zone other = (Zone) obj;
			if (id != other.id)
				return false;
			return true;
		}
		
}

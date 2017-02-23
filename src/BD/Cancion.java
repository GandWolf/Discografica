package BD;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Gand on 23/02/17.
 */
@Entity
public class Cancion {
    private String titulo;
    private Integer duracion;
    private String letra;
    private Integer idAlbum;

    public Cancion(String titulo, int duracion, String letra, int album) {

    }

    @Id
    @Column(name = "titulo", nullable = false, length = 20)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Basic
    @Column(name = "duracion", nullable = true)
    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    @Basic
    @Column(name = "letra", nullable = true, length = 200)
    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    @Basic
    @Column(name = "id_album", nullable = true)
    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cancion cancion = (Cancion) o;

        if (titulo != null ? !titulo.equals(cancion.titulo) : cancion.titulo != null) return false;
        if (duracion != null ? !duracion.equals(cancion.duracion) : cancion.duracion != null) return false;
        if (letra != null ? !letra.equals(cancion.letra) : cancion.letra != null) return false;
        if (idAlbum != null ? !idAlbum.equals(cancion.idAlbum) : cancion.idAlbum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = titulo != null ? titulo.hashCode() : 0;
        result = 31 * result + (duracion != null ? duracion.hashCode() : 0);
        result = 31 * result + (letra != null ? letra.hashCode() : 0);
        result = 31 * result + (idAlbum != null ? idAlbum.hashCode() : 0);
        return result;
    }
}

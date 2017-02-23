package BD;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Gand on 23/02/17.
 */
@Entity
public class Album {
    private int id;
    private String tituloAl;
    private Integer anio;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "titulo_al", nullable = true, length = 20)
    public String getTituloAl() {
        return tituloAl;
    }

    public void setTituloAl(String tituloAl) {
        this.tituloAl = tituloAl;
    }

    @Basic
    @Column(name = "anio", nullable = true)
    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (id != album.id) return false;
        if (tituloAl != null ? !tituloAl.equals(album.tituloAl) : album.tituloAl != null) return false;
        if (anio != null ? !anio.equals(album.anio) : album.anio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tituloAl != null ? tituloAl.hashCode() : 0);
        result = 31 * result + (anio != null ? anio.hashCode() : 0);
        return result;
    }
}

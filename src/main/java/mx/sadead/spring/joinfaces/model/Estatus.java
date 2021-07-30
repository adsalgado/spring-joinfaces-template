package mx.sadead.spring.joinfaces.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jlopez
 */
@Entity
@Table(name = "cfg_estatus")
@XmlRootElement
public class Estatus implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "tipo_estatus_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = true)
    private TipoEstatus tipoEstatus;
    @Basic(optional = false)
    @Column(name = "tipo_estatus_id")
    private Integer tipoEstatusId;


    public Estatus() {
    }

    public Estatus(Integer id) {
        this.id = id;
    }

    public Estatus(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoEstatus getTipoEstatus() {
        return tipoEstatus;
    }

    public void setTipoEstatus(TipoEstatus tipoEstatus) {
        this.tipoEstatus = tipoEstatus;
    }

    public Integer getTipoEstatusId() {
        return tipoEstatusId;
    }

    public void setTipoEstatusId(Integer tipoEstatusId) {
        this.tipoEstatusId = tipoEstatusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estatus)) {
            return false;
        }
        Estatus other = (Estatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mx.sharktech.pojos.Estatus[ id=" + id + " ]";
    }

}

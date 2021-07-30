package mx.sadead.spring.joinfaces.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jlopez
 */
@Entity
@Table(name = "cfg_tipo_estatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEstatus.findAll", query = "SELECT t FROM TipoEstatus t")
    , @NamedQuery(name = "TipoEstatus.findById", query = "SELECT t FROM TipoEstatus t WHERE t.id = :id")
    , @NamedQuery(name = "TipoEstatus.findByNombre", query = "SELECT t FROM TipoEstatus t WHERE t.nombre = :nombre")})
public class TipoEstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final Integer TIPO_ESTATUS_USUARIO = 1; 
    public static final Integer TIPO_ESTATUS_ARCHIVO_DIGITAL = 2; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEstatusId")
    private List<Estatus> estatusList;

    public TipoEstatus() {
    }

    public TipoEstatus(Integer id) {
        this.id = id;
    }

    public TipoEstatus(Integer id, String nombre) {
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

    @XmlTransient
    public List<Estatus> getEstatusList() {
        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
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
        if (!(object instanceof TipoEstatus)) {
            return false;
        }
        TipoEstatus other = (TipoEstatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mx.sharktech.pojos.TipoEstatus[ id=" + id + " ]";
    }
    
}

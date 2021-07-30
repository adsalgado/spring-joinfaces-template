package mx.sadead.spring.joinfaces.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adrián Salgado
 */
@Entity
@Table(name = "cfg_usuario")
@XmlRootElement
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "paterno")
    private String paterno;
    @Column(name = "materno")
    private String materno;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "activo")
    private Integer activo;
    @Basic(optional = false)
    @Column(name = "estatus_id")
    private Integer estatusId;
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fecha_ult_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltModificacion;
    @Column(name = "no_accesos")
    private Integer noAccesos;
    @Column(name = "no_intentos")
    private Integer noIntentos;
    @Column(name = "cuenta_bloqueada")
    private String cuentaBloqueada;
    @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpiracion;
    
    @JoinColumn(name = "estatus_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estatus estatus;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
//    private List<UsuarioRol> roles;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String nombre, String paterno, Integer activo, String userName, String password) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.activo = activo;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public Integer getNoAccesos() {
        return noAccesos;
    }

    public void setNoAccesos(Integer noAccesos) {
        this.noAccesos = noAccesos;
    }

    public Integer getNoIntentos() {
        return noIntentos;
    }

    public void setNoIntentos(Integer noIntentos) {
        this.noIntentos = noIntentos;
    }

    public String getCuentaBloqueada() {
        return cuentaBloqueada;
    }

    public void setCuentaBloqueada(String cuentaBloqueada) {
        this.cuentaBloqueada = cuentaBloqueada;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
//
//    @XmlTransient
//    public List<UsuarioRol> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<UsuarioRol> roles) {
//        this.roles = roles;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.sharkit.web.models.Usuario[ id=" + id + " ]";
    }
    
}

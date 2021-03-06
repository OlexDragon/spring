package jk.web.entities.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Oleksandr Potomkin
 */
@Entity(name = "title")
@Table(name = "titles")
@XmlRootElement
public class TitleEntity implements Serializable {
	private static final long serialVersionUID = 8008156184990118907L;

	public static final int MR		= 1;
    public static final int MRS		= 2;
    public static final int MISS 	= 3;
    public static final int MS		= 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "title_id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "title_name")
    private String name;

    @OneToMany(mappedBy = "titleEntity", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<UserEntity> userEntityList;

    public TitleEntity() {
    }

    public TitleEntity(Integer id) {
        this.id = id;
    }

    public TitleEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null ? obj.hashCode()==hashCode() : false;
    }

	@Override
	public String toString() {
		return "TitleEntity [id=" + id + ", name=" + name + "]";
	}
}

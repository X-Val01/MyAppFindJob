package KhvalAlexandr.myjob.UsersInfo;

public class NewAnnoucement{
    private String nameAnnoucement,descriptionAnnoucement,categoryAnnoucement;

    public String getCategoryAnnoucement() {
        return categoryAnnoucement;
    }

    public void setCategoryAnnoucement(String categoryAnnoucement) {
        this.categoryAnnoucement = categoryAnnoucement;
    }

    public NewAnnoucement(String nameAnnoucement, String descriptionAnnoucement, String categoryAnnoucement){
        this.nameAnnoucement = nameAnnoucement;
        this.descriptionAnnoucement = descriptionAnnoucement;
        this.categoryAnnoucement = categoryAnnoucement;
    }

    public String getNameAnnoucement() {
        return nameAnnoucement;
    }

    public void setNameAnnoucement(String nameAnnoucement) {
        this.nameAnnoucement = nameAnnoucement;
    }

    public String getDescriptionAnnoucement() {
        return descriptionAnnoucement;
    }

    public void setDescriptionAnnoucement(String descriptionAnnoucement) {
        this.descriptionAnnoucement = descriptionAnnoucement;
    }




public  NewAnnoucement() {

}

}

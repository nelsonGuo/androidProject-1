package ca.bcit.ass2.findwater;

import java.util.List;

/**
 * Created by nelson on 10/11/2017.
 */

public class Fountain {

   private String json_featuretype;

    private String Name;

    private int StructureId;

    private String StructureType;

    private String InstallYear;

    private String TCACode;

    private String BrandModel;

    private String SupplierName;

    private String ContractorName;

    private String DesignerName;

    private String Cost;

    private String Comments;

    private String ParkName;

    private String DedicationContact;

    private String DedicationSuite;

    private String DedicationCivicNumber;

    private String DedicationStreetName;

    private String DedicationCity;

    private String DedicationProvince;

    private String DedicationPostalCode;

    private String DedicationPhone;

    private String DedicationInscription;

    private String DedicationDate;

    private String DedicationComments;

    private int OBJECTID;

    private String X;

    private String Y;

    private Json_geometry json_geometry;

    public Fountain(String parkName){

        this.ParkName = parkName;
    }

    public void setJson_featuretype(String json_featuretype){
        this.json_featuretype = json_featuretype;
    }
    public String getJson_featuretype(){
        return this.json_featuretype;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setStructureId(int StructureId){
        this.StructureId = StructureId;
    }
    public int getStructureId(){
        return this.StructureId;
    }
    public void setStructureType(String StructureType){
        this.StructureType = StructureType;
    }
    public String getStructureType(){
        return this.StructureType;
    }
    public void setInstallYear(String InstallYear){
        this.InstallYear = InstallYear;
    }
    public String getInstallYear(){
        return this.InstallYear;
    }
    public void setTCACode(String TCACode){
        this.TCACode = TCACode;
    }
    public String getTCACode(){
        return this.TCACode;
    }
    public void setBrandModel(String BrandModel){
        this.BrandModel = BrandModel;
    }
    public String getBrandModel(){
        return this.BrandModel;
    }
    public void setSupplierName(String SupplierName){
        this.SupplierName = SupplierName;
    }
    public String getSupplierName(){
        return this.SupplierName;
    }
    public void setContractorName(String ContractorName){
        this.ContractorName = ContractorName;
    }
    public String getContractorName(){
        return this.ContractorName;
    }
    public void setDesignerName(String DesignerName){
        this.DesignerName = DesignerName;
    }
    public String getDesignerName(){
        return this.DesignerName;
    }
    public void setCost(String Cost){
        this.Cost = Cost;
    }
    public String getCost(){
        return this.Cost;
    }
    public void setComments(String Comments){
        this.Comments = Comments;
    }
    public String getComments(){
        return this.Comments;
    }
    public void setParkName(String ParkName){
        this.ParkName = ParkName;
    }
    public String getParkName(){
        return this.ParkName;
    }
    public void setDedicationContact(String DedicationContact){
        this.DedicationContact = DedicationContact;
    }
    public String getDedicationContact(){
        return this.DedicationContact;
    }
    public void setDedicationSuite(String DedicationSuite){
        this.DedicationSuite = DedicationSuite;
    }
    public String getDedicationSuite(){
        return this.DedicationSuite;
    }
    public void setDedicationCivicNumber(String DedicationCivicNumber){
        this.DedicationCivicNumber = DedicationCivicNumber;
    }
    public String getDedicationCivicNumber(){
        return this.DedicationCivicNumber;
    }
    public void setDedicationStreetName(String DedicationStreetName){
        this.DedicationStreetName = DedicationStreetName;
    }
    public String getDedicationStreetName(){
        return this.DedicationStreetName;
    }
    public void setDedicationCity(String DedicationCity){
        this.DedicationCity = DedicationCity;
    }
    public String getDedicationCity(){
        return this.DedicationCity;
    }
    public void setDedicationProvince(String DedicationProvince){
        this.DedicationProvince = DedicationProvince;
    }
    public String getDedicationProvince(){
        return this.DedicationProvince;
    }
    public void setDedicationPostalCode(String DedicationPostalCode){
        this.DedicationPostalCode = DedicationPostalCode;
    }
    public String getDedicationPostalCode(){
        return this.DedicationPostalCode;
    }
    public void setDedicationPhone(String DedicationPhone){
        this.DedicationPhone = DedicationPhone;
    }
    public String getDedicationPhone(){
        return this.DedicationPhone;
    }
    public void setDedicationInscription(String DedicationInscription){
        this.DedicationInscription = DedicationInscription;
    }
    public String getDedicationInscription(){
        return this.DedicationInscription;
    }
    public void setDedicationDate(String DedicationDate){
        this.DedicationDate = DedicationDate;
    }
    public String getDedicationDate(){
        return this.DedicationDate;
    }
    public void setDedicationComments(String DedicationComments){
        this.DedicationComments = DedicationComments;
    }
    public String getDedicationComments(){
        return this.DedicationComments;
    }
    public void setOBJECTID(int OBJECTID){
        this.OBJECTID = OBJECTID;
    }
    public int getOBJECTID(){
        return this.OBJECTID;
    }
    public void setX(String X){
        this.X = X;
    }
    public String getX(){
        return this.X;
    }
    public void setY(String Y){
        this.Y = Y;
    }
    public String getY(){
        return this.Y;
    }
    public void setJson_geometry(Json_geometry json_geometry){
        this.json_geometry = json_geometry;
    }
    public Json_geometry getJson_geometry(){
        return this.json_geometry;
    }

}
class Json_geometry
{
    private String type;

    private List<Double> coordinates;

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setCoordinates(List<Double> coordinates){
        this.coordinates = coordinates;
    }
    public List<Double> getCoordinates(){
        return this.coordinates;
    }
}


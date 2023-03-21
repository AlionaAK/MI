package entity;

public class Company extends AbstractEntity {

    private Segment segment;
    private String name;
    private String country;
    //
    private double succor; ///выручка
    private double incomeTax;//расходы по налогу на прибыль
    private double financialIncome; //итогоая финансовая прибыль
    private double depreciation;// амортизация
    private double ebitda; //мультипликатор ebitda
    //
    private ProductMarkStatus status;


    public Company() {
    }


    public Company(String id) {
        super(id);
    }

    public Company(String id, Segment segment, String name, String country, double succor, double incomeTax,
                   double financialIncome, double depreciation, double ebitda, ProductMarkStatus status) {
        super(id);
        this.segment = segment;
        this.name = name;
        this.country = country;
        this.succor = succor;
        this.incomeTax = incomeTax;
        this.financialIncome = financialIncome;
        this.depreciation = depreciation;
        this.ebitda = ebitda;
        this.status = status;
    }


    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSuccor() {
        return succor;
    }

    public void setSuccor(double succor) {
        this.succor = succor;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public double getFinancialIncome() {
        return financialIncome;
    }

    public void setFinancialIncome(double financialIncome) {
        this.financialIncome = financialIncome;
    }

    public double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }

    public double getEbitda() {
        return ebitda;
    }

    public void setEbitda(double ebitda) {
        this.ebitda = ebitda;
    }

    public ProductMarkStatus getStatus() {
        return status;
    }

    public void setStatus(ProductMarkStatus status) {
        this.status = status;
    }
}

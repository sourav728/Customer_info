package com.example.tvd.customer_info.values;

/**
 * Created by TVD on 4/17/2018.
 */

public class GetSetValues {
    private String latitude,longitude,login_id,consumer_id,rrno,relationship,spiner_item;
    private String cons_name,cons_address,cons_etv_meter,cons_kwhp,cons_tariff,cons_date_of_service,cons_mobile_no,cons_email,cons_subdivision;
    private String view_bill_date1,view_bill_due_date,view_bill_cons_name,view_bill_add,view_bill_mobileno,view_bill_email,
                   view_bill_cons_id,view_bill_rrno,view_bill_etv_meter,view_bill_kwhp,view_bill_tariff,view_bill_prevread,
                   view_bill_curr_read,view_bill_con,view_bill_avgcon,view_bill_fc,view_bill_ec,view_bill_fac,view_bill_rbtamt,
                   view_bill_pfpanelty,view_bill_bmdpanelty,view_bill_di,view_bill_do,view_bill_dt,view_bill_netpayable,
                   view_bill_arrears,view_bill_adjamt,view_bill_dg;
    private String complaint_customer_name,complaint_rrno,complaint_feeder_name,complaint_tc_code,
                   complaint_subdivision_code,complaint_poleno,complaint_tariff, complaint_cons_no, complaint_mobile_no, complaint_add;
    private String complaint_type,complaint_sub_type;

    private String ltbill_date="",ltbill_con="",ltbill_arrears="",ltbill_demand="";
    private String lt_receiptdate="",lt_collection_amt="",lt_receiptno="",lt_collection_counter="";
    private String lt_consumer_id="",lt_consumer_name="",lt_tariff="",lt_mobile_no="";
    private String lt_billing_consumer_id="",lt_billing_consumer_name="",lt_billing_tariff="",lt_billing_mobile_no="";
    private String last_receipt_date="",last_payment_amt="";
    public String getLatitude() {
        return latitude;
    }

    public String getView_bill_date1() {
        return view_bill_date1;
    }

    public String getLt_billing_consumer_id() {
        return lt_billing_consumer_id;
    }

    public String getLast_receipt_date() {
        return last_receipt_date;
    }

    public void setLast_receipt_date(String last_receipt_date) {
        this.last_receipt_date = last_receipt_date;
    }

    public String getLast_payment_amt() {
        return last_payment_amt;
    }

    public void setLast_payment_amt(String last_payment_amt) {
        this.last_payment_amt = last_payment_amt;
    }

    public void setLt_billing_consumer_id(String lt_billing_consumer_id) {
        this.lt_billing_consumer_id = lt_billing_consumer_id;
    }

    public String getLt_billing_consumer_name() {
        return lt_billing_consumer_name;
    }

    public void setLt_billing_consumer_name(String lt_billing_consumer_name) {
        this.lt_billing_consumer_name = lt_billing_consumer_name;
    }

    public String getLt_billing_tariff() {
        return lt_billing_tariff;
    }

    public void setLt_billing_tariff(String lt_billing_tariff) {
        this.lt_billing_tariff = lt_billing_tariff;
    }

    public String getLt_billing_mobile_no() {
        return lt_billing_mobile_no;
    }

    public void setLt_billing_mobile_no(String lt_billing_mobile_no) {
        this.lt_billing_mobile_no = lt_billing_mobile_no;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public String getComplaint_sub_type() {
        return complaint_sub_type;
    }

    public void setComplaint_sub_type(String complaint_sub_type) {
        this.complaint_sub_type = complaint_sub_type;
    }

    public String getComplaint_customer_name() {
        return complaint_customer_name;
    }

    public String getComplaint_cons_no() {
        return complaint_cons_no;
    }

    public String getComplaint_add() {
        return complaint_add;
    }

    public void setComplaint_add(String complaint_add) {
        this.complaint_add = complaint_add;
    }

    public String getLtbill_date() {
        return ltbill_date;
    }

    public void setLtbill_date(String ltbill_date) {
        this.ltbill_date = ltbill_date;
    }

    public String getLtbill_con() {
        return ltbill_con;
    }

    public void setLtbill_con(String ltbill_con) {
        this.ltbill_con = ltbill_con;
    }

    public String getLtbill_arrears() {
        return ltbill_arrears;
    }

    public void setLtbill_arrears(String ltbill_arrears) {
        this.ltbill_arrears = ltbill_arrears;
    }

    public String getLtbill_demand() {
        return ltbill_demand;
    }

    public void setLtbill_demand(String ltbill_demand) {
        this.ltbill_demand = ltbill_demand;
    }

    public String getLt_receiptdate() {
        return lt_receiptdate;
    }

    public void setLt_receiptdate(String lt_receiptdate) {
        this.lt_receiptdate = lt_receiptdate;
    }

    public String getLt_collection_amt() {
        return lt_collection_amt;
    }

    public void setLt_collection_amt(String lt_collection_amt) {
        this.lt_collection_amt = lt_collection_amt;
    }

    public String getLt_receiptno() {
        return lt_receiptno;
    }

    public void setLt_receiptno(String lt_receiptno) {
        this.lt_receiptno = lt_receiptno;
    }

    public String getLt_collection_counter() {
        return lt_collection_counter;
    }

    public void setLt_collection_counter(String lt_collection_counter) {
        this.lt_collection_counter = lt_collection_counter;
    }

    public String getLt_consumer_id() {
        return lt_consumer_id;
    }

    public void setLt_consumer_id(String lt_consumer_id) {
        this.lt_consumer_id = lt_consumer_id;
    }

    public String getLt_consumer_name() {
        return lt_consumer_name;
    }

    public void setLt_consumer_name(String lt_consumer_name) {
        this.lt_consumer_name = lt_consumer_name;
    }

    public String getLt_tariff() {
        return lt_tariff;
    }

    public void setLt_tariff(String lt_tariff) {
        this.lt_tariff = lt_tariff;
    }

    public String getLt_mobile_no() {
        return lt_mobile_no;
    }

    public void setLt_mobile_no(String lt_mobile_no) {
        this.lt_mobile_no = lt_mobile_no;
    }

    public void setComplaint_cons_no(String complaint_cons_no) {
        this.complaint_cons_no = complaint_cons_no;

    }

    public String getComplaint_mobile_no() {
        return complaint_mobile_no;
    }

    public void setComplaint_mobile_no(String complaint_mobile_no) {
        this.complaint_mobile_no = complaint_mobile_no;
    }

    public void setComplaint_customer_name(String complaint_customer_name) {
        this.complaint_customer_name = complaint_customer_name;
    }

    public String getComplaint_rrno() {
        return complaint_rrno;
    }

    public void setComplaint_rrno(String complaint_rrno) {
        this.complaint_rrno = complaint_rrno;
    }


    public String getComplaint_feeder_name() {
        return complaint_feeder_name;
    }

    public void setComplaint_feeder_name(String complaint_feeder_name) {
        this.complaint_feeder_name = complaint_feeder_name;
    }

    public String getComplaint_tc_code() {
        return complaint_tc_code;
    }

    public void setComplaint_tc_code(String complaint_tc_code) {
        this.complaint_tc_code = complaint_tc_code;
    }

    public String getComplaint_subdivision_code() {
        return complaint_subdivision_code;
    }

    public void setComplaint_subdivision_code(String complaint_subdivision_code) {
        this.complaint_subdivision_code = complaint_subdivision_code;
    }

    public String getComplaint_poleno() {
        return complaint_poleno;
    }

    public void setComplaint_poleno(String complaint_poleno) {
        this.complaint_poleno = complaint_poleno;
    }

    public String getComplaint_tariff() {
        return complaint_tariff;
    }

    public void setComplaint_tariff(String complaint_tariff) {
        this.complaint_tariff = complaint_tariff;
    }

    public void setView_bill_date1(String view_bill_date1) {
        this.view_bill_date1 = view_bill_date1;
    }

    public String getView_bill_due_date() {
        return view_bill_due_date;
    }

    public void setView_bill_due_date(String view_bill_due_date) {
        this.view_bill_due_date = view_bill_due_date;
    }

    public String getView_bill_cons_name() {
        return view_bill_cons_name;
    }

    public void setView_bill_cons_name(String view_bill_cons_name) {
        this.view_bill_cons_name = view_bill_cons_name;
    }

    public String getView_bill_add() {
        return view_bill_add;
    }

    public void setView_bill_add(String view_bill_add) {
        this.view_bill_add = view_bill_add;
    }

    public String getView_bill_mobileno() {
        return view_bill_mobileno;
    }

    public void setView_bill_mobileno(String view_bill_mobileno) {
        this.view_bill_mobileno = view_bill_mobileno;
    }

    public String getView_bill_email() {
        return view_bill_email;
    }

    public void setView_bill_email(String view_bill_email) {
        this.view_bill_email = view_bill_email;
    }

    public String getView_bill_cons_id() {
        return view_bill_cons_id;
    }

    public void setView_bill_cons_id(String view_bill_cons_id) {
        this.view_bill_cons_id = view_bill_cons_id;
    }

    public String getView_bill_rrno() {
        return view_bill_rrno;
    }

    public void setView_bill_rrno(String view_bill_rrno) {
        this.view_bill_rrno = view_bill_rrno;
    }

    public String getView_bill_etv_meter() {
        return view_bill_etv_meter;
    }

    public void setView_bill_etv_meter(String view_bill_etv_meter) {
        this.view_bill_etv_meter = view_bill_etv_meter;
    }

    public String getView_bill_kwhp() {
        return view_bill_kwhp;
    }

    public void setView_bill_kwhp(String view_bill_kwhp) {
        this.view_bill_kwhp = view_bill_kwhp;
    }

    public String getView_bill_tariff() {
        return view_bill_tariff;
    }

    public void setView_bill_tariff(String view_bill_tariff) {
        this.view_bill_tariff = view_bill_tariff;
    }

    public String getView_bill_prevread() {
        return view_bill_prevread;
    }

    public void setView_bill_prevread(String view_bill_prevread) {
        this.view_bill_prevread = view_bill_prevread;
    }

    public String getView_bill_curr_read() {
        return view_bill_curr_read;
    }

    public void setView_bill_curr_read(String view_bill_curr_read) {
        this.view_bill_curr_read = view_bill_curr_read;
    }

    public String getView_bill_con() {
        return view_bill_con;
    }

    public void setView_bill_con(String view_bill_con) {
        this.view_bill_con = view_bill_con;
    }

    public String getView_bill_avgcon() {
        return view_bill_avgcon;
    }

    public void setView_bill_avgcon(String view_bill_avgcon) {
        this.view_bill_avgcon = view_bill_avgcon;
    }

    public String getView_bill_fc() {
        return view_bill_fc;
    }

    public void setView_bill_fc(String view_bill_fc) {
        this.view_bill_fc = view_bill_fc;
    }

    public String getView_bill_ec() {
        return view_bill_ec;
    }

    public void setView_bill_ec(String view_bill_ec) {
        this.view_bill_ec = view_bill_ec;
    }

    public String getView_bill_fac() {
        return view_bill_fac;
    }

    public void setView_bill_fac(String view_bill_fac) {
        this.view_bill_fac = view_bill_fac;
    }

    public String getView_bill_rbtamt() {
        return view_bill_rbtamt;
    }

    public void setView_bill_rbtamt(String view_bill_rbtamt) {
        this.view_bill_rbtamt = view_bill_rbtamt;
    }

    public String getView_bill_pfpanelty() {
        return view_bill_pfpanelty;
    }

    public void setView_bill_pfpanelty(String view_bill_pfpanelty) {
        this.view_bill_pfpanelty = view_bill_pfpanelty;
    }

    public String getView_bill_bmdpanelty() {
        return view_bill_bmdpanelty;
    }

    public void setView_bill_bmdpanelty(String view_bill_bmdpanelty) {
        this.view_bill_bmdpanelty = view_bill_bmdpanelty;
    }

    public String getView_bill_di() {
        return view_bill_di;
    }

    public void setView_bill_di(String view_bill_di) {
        this.view_bill_di = view_bill_di;
    }

    public String getView_bill_do() {
        return view_bill_do;
    }

    public void setView_bill_do(String view_bill_do) {
        this.view_bill_do = view_bill_do;
    }

    public String getView_bill_dt() {
        return view_bill_dt;
    }

    public void setView_bill_dt(String view_bill_dt) {
        this.view_bill_dt = view_bill_dt;
    }

    public String getView_bill_netpayable() {
        return view_bill_netpayable;
    }

    public void setView_bill_netpayable(String view_bill_netpayable) {
        this.view_bill_netpayable = view_bill_netpayable;
    }

    public String getView_bill_arrears() {
        return view_bill_arrears;
    }

    public void setView_bill_arrears(String view_bill_arrears) {
        this.view_bill_arrears = view_bill_arrears;
    }

    public String getView_bill_adjamt() {
        return view_bill_adjamt;
    }

    public void setView_bill_adjamt(String view_bill_adjamt) {
        this.view_bill_adjamt = view_bill_adjamt;
    }

    public String getView_bill_dg() {
        return view_bill_dg;
    }

    public void setView_bill_dg(String view_bill_dg) {
        this.view_bill_dg = view_bill_dg;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCons_name() {
        return cons_name;
    }

    public void setCons_name(String cons_name) {
        this.cons_name = cons_name;
    }

    public String getCons_address() {
        return cons_address;
    }

    public void setCons_address(String cons_address) {
        this.cons_address = cons_address;
    }

    public String getCons_etv_meter() {
        return cons_etv_meter;
    }

    public void setCons_etv_meter(String cons_etv_meter) {
        this.cons_etv_meter = cons_etv_meter;
    }

    public String getCons_kwhp() {
        return cons_kwhp;
    }

    public void setCons_kwhp(String cons_kwhp) {
        this.cons_kwhp = cons_kwhp;
    }

    public String getCons_tariff() {
        return cons_tariff;
    }

    public void setCons_tariff(String cons_tariff) {
        this.cons_tariff = cons_tariff;
    }

    public String getCons_date_of_service() {
        return cons_date_of_service;
    }

    public void setCons_date_of_service(String cons_date_of_service) {
        this.cons_date_of_service = cons_date_of_service;
    }

    public String getCons_mobile_no() {
        return cons_mobile_no;
    }

    public void setCons_mobile_no(String cons_mobile_no) {
        this.cons_mobile_no = cons_mobile_no;
    }

    public String getCons_email() {
        return cons_email;
    }

    public void setCons_email(String cons_email) {
        this.cons_email = cons_email;
    }

    public String getCons_subdivision() {
        return cons_subdivision;
    }

    public void setCons_subdivision(String cons_subdivision) {
        this.cons_subdivision = cons_subdivision;
    }

    public String getSpiner_item() {
        return spiner_item;
    }

    public void setSpiner_item(String spiner_item) {
        this.spiner_item = spiner_item;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getRrno() {
        return rrno;
    }

    public void setRrno(String rrno) {
        this.rrno = rrno;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

package co.th.ktc.nfe.report.dao.impl;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import co.th.ktc.nfe.report.dao.AbstractReportDao;

@Service(value="applicationReceiveDao")
public class ApplicationReceiveDao extends AbstractReportDao {

	public ApplicationReceiveDao() {
	}

	@Override
	public void insert(Object[] parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object[] parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object[] parameter) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public SqlRowSet query(Object[] parameter) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("          select *    ");
		sql.append("          from (   ");
		sql.append("                 select to_char(a.app_datetime,'DD/MM/YYYY') as date_rec    ");
		sql.append("                     , case  when B.GROUPPRODUCT_TYPE='B' then 'BD'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='C' or B.GROUPPRODUCT_LOANTYPE='B' then 'CC'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='F' then 'PL'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='R' then 'RL'   ");
		sql.append("                     else '-' end as GROUPPRODUCT_LOANTYPE   ");
		sql.append("                     , a.app_vsource AS APP_VSOURCE  ");
		sql.append("                     , a.app_no as  APPLICATIONID   ");
		sql.append("                     , a.app_thaifname ||' ' || a.APP_THAILNAME as ThaiName  ");
		sql.append("                     ,A.APP_CITIZENID as CitizenID    ");
		sql.append("                     ,'M' as GROUPPRODUCT_TYPE    ");
		sql.append("                     ,nvl(mspr.product_description||'-'||mssub.subproduct_description,' ') as Product_SubProduct    ");
		sql.append("                     ,a.app_createby  AS CREATEBY  ");
		sql.append("                     ,a.App_Chkncb as NcbStatus   ");
		sql.append("                     ,a.app_barcode2 as APPBARCODE   ");
		sql.append("                     ,a.app_SourceCode as  SOURCECODE   ");
		sql.append("                     ,a.app_Agent AS AGENT  ");
		sql.append("                     ,a.app_Branch AS BRANCH   ");
		sql.append("                     ,APPS.appstatus_description as Queuee   ");
		sql.append("                     ,nvl(APPS1.appstatus_description,'-') as appstatus_description   ");
		sql.append("  ");
		sql.append("                     from nfe_application a   ");
		sql.append("                     left join nfe_app_product apr on a.app_no = apr.appproduct_appno    ");
		sql.append("                     left join nfe_ms_appstatus apps on A.APP_STATUSCODE = APPS.APPSTATUS_CODE   ");
		sql.append("                     left join nfe_app_resolve resv on resv.resolve_appproductId = APR.APPPRODUCT_ID   ");
		sql.append("                     left join nfe_ms_appstatus apps1 on RESV.RESOLVE_STATUSCODE = APPS1.APPSTATUS_CODE    ");
		sql.append("                     Left Join nfe_ms_groupproduct b on a.app_groupproduct=b.groupproduct_id    ");
		sql.append("                     left join nfe_ms_subproduct mssub on apr.appproduct_subproductid = mssub.subproduct_id  ");
		sql.append("                     left join nfe_ms_product mspr on apr.appproduct_productid = mspr.product_id    ");
		sql.append("                     left join nfe_ms_groupproduct msgr on mspr.product_groupproductid = msgr.groupproduct_id    ");
		sql.append("                 where 1=1   ");
		sql.append("  ");
		sql.append("                 and (B.GROUPPRODUCT_LOANTYPE in ('C','B') OR (B.GROUPPRODUCT_TYPE = 'B' AND MSGR.GROUPPRODUCT_LOANTYPE in ('C','B') ) )   ");
		sql.append("                 and  A.APP_STATUSCODE <> '2C'   ");
		sql.append("                 and  B.GROUPPRODUCT_TYPE <> 'S'  ");
		sql.append("                  ");
		sql.append("                 and (a.app_datetime  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')  ");
		sql.append("                      AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss'))  ");
		sql.append("  ");
		sql.append("          union   ");
		sql.append("  ");
		sql.append("                 select to_char(a.app_datetime,'DD/MM/YYYY') as date_rec    ");
		sql.append("                     , case  when B.GROUPPRODUCT_TYPE='B' then 'BD'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='C' or B.GROUPPRODUCT_LOANTYPE='B' then 'CC'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='F' then 'PL'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='R' then 'RL'   ");
		sql.append("                     else '-' end as GROUPPRODUCT_LOANTYPE   ");
		sql.append("                     , a.app_vsource AS APP_VSOURCE  ");
		sql.append("                     , SUP.APPSUP_APPNO as APPLICATIONID    ");
		sql.append("                     , SUP.APPSUP_ENGFNAME || ' ' || SUP.APPSUP_ENGLNAME as ThaiName  ");
		sql.append("                     ,SUP.APPSUP_CITIZENID as CitizenID    ");
		sql.append("                     ,'S' as GROUPPRODUCT_TYPE    ");
		sql.append("                     ,nvl(mspr.product_description||'-'||mssub.subproduct_description,' ') as Product_SubProduct    ");
		sql.append("                     ,a.app_createby AS CREATEBY   ");
		sql.append("                     ,a.App_Chkncb as NcbStatus   ");
		sql.append("                     ,a.app_barcode2 as APPBARCODE   ");
		sql.append("                     ,a.app_SourceCode as  SOURCECODE  ");
		sql.append("                     ,a.app_Agent AS AGENT   ");
		sql.append("                     ,a.app_Branch  AS BRANCH   ");
		sql.append("                     ,APPS.appstatus_description as Queuee   ");
		sql.append("                     ,nvl(APPS1.appstatus_description,'-') as appstatus_description   ");
		sql.append("          ");
		sql.append("                     from nfe_application a    ");
		sql.append("                     inner join nfe_app_supplementary sup on A.APP_NO = SUP.APPSUP_APPNO  ");
		sql.append("                     left join nfe_ms_appstatus apps on A.APP_STATUSCODE = APPS.APPSTATUS_CODE   ");
		sql.append("                     left join nfe_app_resolve resv on resv.resolve_appproductId = SUP.APPSUP_ID   ");
		sql.append("                     left join nfe_ms_appstatus apps1 on RESV.RESOLVE_STATUSCODE = APPS1.APPSTATUS_CODE     ");
		sql.append("  ");
		sql.append("                     Left Join nfe_ms_groupproduct b on a.app_groupproduct=b.groupproduct_id    ");
		sql.append("                     left join nfe_ms_subproduct mssub on SUP.APPSUP_SUBPRODUCT = mssub.subproduct_id    ");
		sql.append("                     left join nfe_ms_product mspr on SUP.APPSUP_PRODUCT = mspr.product_id    ");
		sql.append("                     left join nfe_ms_groupproduct msgr on mspr.product_groupproductid = msgr.groupproduct_id     ");
		sql.append("                 where 1=1   ");
		sql.append("  ");
		sql.append("                 and (B.GROUPPRODUCT_LOANTYPE in ('C','B') OR (B.GROUPPRODUCT_TYPE = 'B' AND MSGR.GROUPPRODUCT_LOANTYPE in ('C','B') ) )         ");
		sql.append("                 and  A.APP_STATUSCODE <> '2C'   ");
		sql.append("  ");
		sql.append("                 and (a.app_datetime  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')  ");
		sql.append("                      AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss'))  ");
		sql.append("          ) temp    ");
		sql.append("          order by APPLICATIONID desc, GROUPPRODUCT_TYPE asc   ");

		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}
	
	@Override
	public SqlRowSet query(Object[] parameter,String sheetName) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("  select *    ");
		sql.append("          from (   ");
		sql.append("                 select to_char(a.app_datetime,'DD/MM/YYYY') as date_rec    ");
		sql.append("                     , case  when B.GROUPPRODUCT_TYPE='B' then 'BD'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='C' or B.GROUPPRODUCT_LOANTYPE='B' then 'CC'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='F' then 'PL'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='R' then 'RL'   ");
		sql.append("                     else '-' end as GROUPPRODUCT_LOANTYPE   ");
		sql.append("                     , a.app_vsource as APP_VSOURCE   ");
		sql.append("                     , a.app_no as ApplicationID    ");
		sql.append("                     , a.app_thaifname ||' ' || a.APP_THAILNAME as ThaiName  ");
		sql.append("                     ,A.APP_CITIZENID as CitizenID    ");
		sql.append("                     ,'M' as GROUPPRODUCT_TYPE    ");
		sql.append("                     ,nvl(mspr.product_description||'-'||mssub.subproduct_description,' ') as Product_SubProduct    ");
		sql.append("                     ,a.app_createby as CREATEBY   ");
		sql.append("                     ,a.App_Chkncb as NcbStatus   ");
		sql.append("                     ,a.app_barcode2 as APPBARCODE   ");
		sql.append("                     ,a.app_SourceCode as SOURCECODE   ");
		sql.append("                     ,a.app_Agent as AGENT ");
		sql.append("                     ,a.app_Branch as BRANCH   ");
		sql.append("                     ,APPS.appstatus_description as Queuee   ");
		sql.append("                     ,nvl(APPS1.appstatus_description,'-') as appstatus_description   ");
		
		sql.append("                     from nfe_application a   ");
		sql.append("                     left join nfe_app_product apr on a.app_no = apr.appproduct_appno    ");
		sql.append("                     left join nfe_ms_appstatus apps on A.APP_STATUSCODE = APPS.APPSTATUS_CODE   ");
		sql.append("                     left join nfe_app_resolve resv on resv.resolve_appproductId = APR.APPPRODUCT_ID   ");
		sql.append("                     left join nfe_ms_appstatus apps1 on RESV.RESOLVE_STATUSCODE = APPS1.APPSTATUS_CODE    ");
		sql.append("                     Left Join nfe_ms_groupproduct b on a.app_groupproduct=b.groupproduct_id    ");
		sql.append("                     left join nfe_ms_subproduct mssub on apr.appproduct_subproductid = mssub.subproduct_id  ");
		sql.append("                     left join nfe_ms_product mspr on apr.appproduct_productid = mspr.product_id    ");
		sql.append("                     left join nfe_ms_groupproduct msgr on mspr.product_groupproductid = msgr.groupproduct_id    ");
		sql.append("                 where 1=1   ");
		
		sql.append("                 and (B.GROUPPRODUCT_LOANTYPE = ? OR (B.GROUPPRODUCT_TYPE = 'B' AND MSGR.GROUPPRODUCT_LOANTYPE = ? ) )   ");
		
		sql.append("                 and A.APP_STATUSCODE <> '2C'   ");
		sql.append("                 and B.GROUPPRODUCT_TYPE <> 'S'  ");
		sql.append("                 and (a.app_datetime  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')  ");
		sql.append("                      AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss'))  ");
		sql.append("  ");
		sql.append("          union   ");
		sql.append("  ");
		sql.append("                 select to_char(a.app_datetime,'DD/MM/YYYY') as date_rec    ");
		sql.append("                     , case  when B.GROUPPRODUCT_TYPE='B' then 'BD'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='C' or B.GROUPPRODUCT_LOANTYPE='B' then 'CC'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='F' then 'PL'    ");
		sql.append("                         when B.GROUPPRODUCT_LOANTYPE='R' then 'RL'   ");
		sql.append("                     else '-' end as GROUPPRODUCT_LOANTYPE   ");
		sql.append("                     , a.app_vsource   ");
		sql.append("                     , SUP.APPSUP_APPNO as ApplicationID    ");
		sql.append("                     , SUP.APPSUP_ENGFNAME || ' ' || SUP.APPSUP_ENGLNAME as ThaiName  ");
		sql.append("                     ,SUP.APPSUP_CITIZENID as CitizenID    ");
		sql.append("                     ,'S' as GROUPPRODUCT_TYPE    ");
		sql.append("                     ,nvl(mspr.product_description||'-'||mssub.subproduct_description,' ') as Product_SubProduct    ");
		sql.append("                     ,a.app_createby    ");
		sql.append("                     ,a.App_Chkncb as NcbStatus   ");
		sql.append("                     ,a.app_barcode2    ");
		sql.append("                     ,a.app_SourceCode   ");
		sql.append("                     ,a.app_Agent  ");
		sql.append("                     ,a.app_Branch     ");
		sql.append("                     ,APPS.appstatus_description as Queuee   ");
		sql.append("                     ,nvl(APPS1.appstatus_description,'-') as appstatus_description   ");
		sql.append("                 from nfe_application a    ");
		sql.append("                     inner join nfe_app_supplementary sup on A.APP_NO = SUP.APPSUP_APPNO  ");
		sql.append("                     left join nfe_ms_appstatus apps on A.APP_STATUSCODE = APPS.APPSTATUS_CODE   ");
		sql.append("                     left join nfe_app_resolve resv on resv.resolve_appproductId = SUP.APPSUP_ID   ");
		sql.append("                     left join nfe_ms_appstatus apps1 on RESV.RESOLVE_STATUSCODE = APPS1.APPSTATUS_CODE     ");
		
		sql.append("                     Left Join nfe_ms_groupproduct b on a.app_groupproduct=b.groupproduct_id    ");
		sql.append("                     left join nfe_ms_subproduct mssub on SUP.APPSUP_SUBPRODUCT = mssub.subproduct_id    ");
		sql.append("                     left join nfe_ms_product mspr on SUP.APPSUP_PRODUCT = mspr.product_id    ");
		sql.append("                     left join nfe_ms_groupproduct msgr on mspr.product_groupproductid = msgr.groupproduct_id     ");
		sql.append("                 where 1=1   ");
		
		sql.append("                 and (B.GROUPPRODUCT_LOANTYPE = ? OR (B.GROUPPRODUCT_TYPE = 'B' AND MSGR.GROUPPRODUCT_LOANTYPE = ? ) )   ");
		
		sql.append("                 and A.APP_STATUSCODE <> '2C'  ");
		sql.append("                 and (a.app_datetime  BETWEEN TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss')  ");
		sql.append("                      AND TO_TIMESTAMP( ?, 'DD/MM/YYYY HH24:mi:ss'))  ");
		
		sql.append("          ) temp    ");
		sql.append("          order by ApplicationID desc, GROUPPRODUCT_TYPE asc   ");

		
		SqlRowSet sqlRowSet =
				getJdbcTemplate().queryForRowSet(sql.toString(), parameter);
		
		return sqlRowSet;
	}

	@Override
	public void success(Object[] parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Object[] parameter) {
		// TODO Auto-generated method stub
		
	}

}

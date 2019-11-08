package com.ligaa;

import com.lig.parser.common.entity.Table;
import com.ligaa.parser.mysql.utils.ParseUtil;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class SqlTest



{
    /**
     * 测试直接语句
     */
    @Test
    public void testSql1() throws IOException {
        System.out.println("测试sql");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        String sql = "SELECT A,BBB,CCC,D,E FROM (SELECT A,B BBB,C CCC,D,E FROM (SELECT AA A,BB B,CC C,DD D,EE E FROM (SELECT AA,BB,CC,DD,EE FROM ODB.TAB_TEST) TT) TMP) TTT";
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(sql);
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }
    /**
     * 测试直接语句
     */
    @Test
    public void testSql2() throws IOException {
        System.out.println("测试sql222222");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        String sql = "SELECT PAADM_RowID,PAADM_AdmSrc_DR,PAADM_BillFlag,PAADM_Specialty_DR,PAADM_AdmDocCodeDR,PAADM_SourceRef_DR,PAADM_RefDate,PAADM_RefExpiryDate,PAADM_RefApprovNo,PAADM_Type_of_Patient_Calc,PAADM_ExpAdmDate,PAADM_EstimDischargeDate,PAADM_EstimDischargeTime,PAADM_DischCond_DR,PAADM_OtherDischType,PAADM_Infect_DR,PAADM_OtherInfectType,PAADM_EstimOperDate,PAADM_Oper_DR,PAADM_Urgent,PAADM_AdmReadm,PAADM_PreAdmitted,PAADM_RoomType_DR,PAADM_DietType_DR,PAADM_MainMRADM_DR,PAADM_StudentMRADM_DR,PAADM_ConfidentMessage,PAADM_ApptMethod_DR,PAADM_OrderTime,PAADM_ReferralType,PAADM_Appoint_DR,PAADM_CurrentRoom_DR,PAADM_CurrentWard_DR,PAADM_RefAdm_DR,PAADM_FirstOrReadmis,PAADM_CurrentBed_DR,PAADM_TempLoc_DR,PAADM_MotherAdm_DR,PAADM_MedicalDischarge,PAADM_ConvertDate,PAADM_ConvertTime,PAADM_MedDischDoc_DR,PAADM_RefDocList_DR,PAADM_MaritalStatus_DR,PAADM_HCA_DR,PAADM_Region_DR,PAADM_CurrentProcess_DR,PAADM_PreviousProcess_DR,PAADM_Epissubtype_DR,PAADM_AdmReason_DR,PAADM_CauseInj_DR,PAADM_EmergencyStatus,PAADM_SeenDate,PAADM_SeenTime,PAADM_RegistrationFee,PAADM_ReasonNotRegFee,PAADM_SpecialCategory,PAADM_SpecialDocument,PAADM_LabourAccident_DR,PAADM_FlaggedPatient,PAADM_WaitList_DR,PAADM_FamilyDoctor,PAADM_ReadOnly,PAADM_PersonResponsiblePayment,PAADM_Verified,PAADM_Completed,PAADM_HCP_DR,PAADM_RefClinTo_DR,PAADM_ADMNo,PAADM_RefDocCodeDR,PAADM_AdmRef,PAADM_SeriousDisease,PAADM_MedDischDate,PAADM_MedDischTime,PAADM_TriageDate,PAADM_TriageTime,PAADM_LikelihoodAdmit,PAADM_HumanIntent_DR,PAADM_PlaceOfInj_DR,PAADM_ActivityInjured_DR,PAADM_Occupation_DR,PAADM_PersonRespPayment,PAADM_RequestMR,PAADM_InpatBedReqDate,PAADM_InpatBedReqTime,PAADM_CClass,PAADM_UpdateDate,PAADM_UpdateTime,PAADM_UpdateUser_DR,PAADM_RefDocClinic_DR,PAADM_DischgDate,PAADM_CurrentResource_DR,PAADM_ConfirmReferral,PAADM_RefPeriod_DR,PAADM_OSVisitStatus_DR,PAADM_DateRefDecision,PAADM_MethodRef_DR,PAADM_ReferralPriority_DR,PAADM_TumourSiteGroup_DR,PAADM_ReasonDelDisch_DR,PAADM_HospChaplainVisitReq,PAADM_DischgTime,PAADM_OwnMinisterIndicator,PAADM_GuaranteeAdmDate,PAADM_AEArrivalMode_DR,PAADM_InternalRefDoc_DR,PAADM_HomerID,PAADM_RefDrConsent,PAADM_AdmMethod_DR,PAADM_DateHCPRequestSent,PAADM_DateHCPreceived,PAADM_HCPPriority_DR,PAADM_DischgDoc_DR,PAADM_AppointTransport_DR,PAADM_PoliceDivision,PAADM_SpecificLoc,PAADM_ChaplainName,PAADM_ChaplainChurchAddress,PAADM_ChaplainPhone,PAADM_DateOfEntry,PAADM_InterviewedBy,PAADM_DateOfInterview,PAADM_InterviewRec,PAADM_VisitStatus,PAADM_OverseasVisitor,PAADM_OSVCountryFrom_DR,PAADM_OtherHospital,PAADM_PermanentResident,PAADM_DateReceived,PAADM_SourceOfAttend_DR,PAADM_MajorIncident_DR,PAADM_ReportDate,PAADM_OESubCat_DR,PAADM_RefStat_DR,PAADM_Dispos_DR,PAADM_PostDischStatus_DR,PAADM_DNAReason,PAADM_NursingNotesFlag,PAADM_CancelReason_DR,PAADM_ConsentRecFundInfo,PAADM_ConsentPatSatisfSurve,PAADM_FrequentAdmissions,PAADM_DischComments,PAADM_PatientContacted,PAADM_LikelyTransICU_DR,PAADM_HCR_DR,PAADM_EstDischConfirmed,PAADM_PatClassif_DR,PAADM_ConvertUser_DR,PAADM_InPatAdmType_DR,PAADM_AdmissionFIM,PAADM_DischargeFIM,PAADM_Related,PAADM_TreatingDr_DR,PAADM_ContractSpoke_DR,PAADM_ContractType_DR,PAADM_TrafficAccident_DR,PAADM_ContractRole_DR,PAADM_FundingArrangement_DR,PAADM_BillingMethod_DR,PAADM_PalliativeCare_DR,PAADM_BedMobility_DR,PAADM_Toileting_DR,PAADM_Transfer_DR,PAADM_Eating_DR,PAADM_RUGTotal,PAADM_BarthelScore,PAADM_ShowInFutureEpisode,PAADM_DischargeBarthelScore,PAADM_ClinicalSubProgram_DR,PAADM_ReadmToRehab_DR,PAADM_OnsetDate,PAADM_MiniMentalAssScore,PAADM_TriageNurse_DR,PAADM_StatePPP_DR,PAADM_DaySurgeryType,PAADM_TransferFacility,PAADM_AppointBookingSystem_DR,PAADM_PAPMI_DR,PAADM_ParentWard_DR,PAADM_PatAcuity_DR,PAADM_SecondaryReason_DR,PAADM_DateDischHosp,PAADM_CaseManager_DR,PAADM_DateFirstAppt,PAADM_LastContactFamily,PAADM_RejectionReason,PAADM_LongStay,PAADM_InternalRefLoc_DR,PAADM_DatePatRefferedAssess,PAADM_DateSocialWorkerAlloc,PAADM_DateSocialWorkerCompleted,PAADM_DateAssessmentCompleted,PAADM_SocialWorkerName,PAADM_PatDestination,PAADM_DelayReason,PAADM_HasCommunSocWorker,PAADM_HasFunding,PAADM_AutocodeRequired,PAADM_SocialWorkerCentre_DR,PAADM_LocalAuthority_DR,PAADM_NonGovOrg_DR,PAADM_TempLocDate,PAADM_TempLocTime,PAADM_QualifStatus_DR,PAADM_SuspectCancerType_DR,PAADM_Pregnancy_DR,PAADM_RefToNonGov_DR,PAADM_RefToNGOContactName,PAADM_RefOrgAddress,PAADM_DischgHospital,PAADM_Hospital_DR,PAADM_ViewablebyEpCareProv,PAADM_MaternityVisType_DR,PAADM_LabClinicalCondition,PAADM_TelephoneActive,PAADM_CodingUpdateDate,PAADM_CodingUpdateTime,PAADM_CodingUpdateUser_DR,PAADM_Confidential,PAADM_CreatePreadmission,PAADM_BookLocReady,PAADM_DaysCarriedForward,PAADM_RBCCancelReason_DR,PAADM_UpdateUserHospital_DR,PAADM_ConsentDVA,PAADM_ConsentWorkCompens,PAADM_ConsentMotorVehicle,PAADM_ConsentDepOfDefence,PAADM_ConsentFeedback,PAADM_PrevInPatAdmPalliativeCare,PAADM_PrevNonInPatPalliativeCare,PAADM_DoctorLetterNotes,PAADM_PatientLetterNotes,PAADM_AuditLetterResponse,PAADM_UsualAccom_DR,PAADM_FinDischDate,PAADM_FinDischTime,PAADM_InjuryIncidentDate,PAADM_PAAdm2_DR,PAADM_PatType_DR,PAADM_DepCode_DR,PAADM_AdmCateg_DR,PAADM_ExpLOS,PAADM_DischargeAppoint_DR,PAADM_AdmDate,PAADM_RefClinic_DR,PAADM_InPatNo,PAADM_EmergencyNo,PAADM_PreAdmNo,PAADM_ConvertedFromOut,PAADM_Priority_DR,PAADM_PreAdmitDate,PAADM_AdmTime,PAADM_PreAdmitTime,PAADM_Isolate,PAADM_EmergencyDate,PAADM_EmergencyTime,PAADM_Type,PAADM_PaidEmergencySurcharge,PAADM_OriginalAdmissionDR,PAADM_MealPreference,PAADM_CreateDate,PAADM_CreateTime,PAADM_CreateUser,PAADM_Remark,PAADM_Current FROM SQLUser.PA_Adm";
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(sql);
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }
    /**
     * 测试直接语句
     */
    @Test
    public void testSql11() throws IOException {
        System.out.println("select [门诊费用]/[门诊人次] ");
        String sql = "select 门诊费用/门诊人次 ";
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(sql);
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }
    /**
     * 测试文件
     */
    /*@Test
    public void testSql2() throws IOException {
        System.out.println("测试文件sql");
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(new InputStreamReader(SqlTest.class.getResourceAsStream("/semples/Query.sql")));
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }*/
    /**
     * 测试文件
     */
    /*@Test
    public void testSql3() throws IOException {
        System.out.println("测试文件sql");
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(new InputStreamReader(SqlTest.class.getResourceAsStream("/semples/Query2.sql")));
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }*/
    /**
     * 测试直接语句
     */
    @Test
    public void testSqlUnion() throws IOException {
        System.out.println("testSqlUnion");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        String sql = "SELECT A,B,C FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'test' UNION SELECT A,B,C FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'test' UNION SELECT A,B,C FROM information_schema.ROUTINES WHERE ROUTINE_SCHEMA = 'test'";
        long startTime = System.currentTimeMillis();
        List<Table> tables = ParseUtil.parseSelect(sql);
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(JSONObject.toJSONString(tables));
    }
    /**
     * 测试裁剪SQL
     */
    @Test
    public void testSqltrim() throws IOException {
        System.out.println("testSqltrim");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        //String sql = "select * from user u join amount a on u.id = a.pid join goods g on u.id = g.pid and g.oderId = a.orderid where  sex > '?1' and age between '?2' and '?3' and ((province in ('?4') or sex = '?5') and name like '?6')";
        String sql = "select * from ab_op_feelist  where serialno = '?' and feeno = '?'  limit 10 ";
        Map<Integer,Object> params = new HashMap<>();
        params.put(1,"aaa");
        /*params.put(2,"  ");
        params.put(3,"  ");
        params.put(4,"  ");
        params.put(5,"  ");
        params.put(6,"  ");*/
        long startTime = System.currentTimeMillis();
        System.out.println(ParseUtil.trimSql(sql,params));
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
    }
    /**
     * 测试裁剪SQL
     */
    @Test
    public void testSqltrim2() throws IOException {
        System.out.println("testSqltrim2");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        String sql = "select * from user u join amount a on u.id = a.pid join goods g on u.id = g.pid and g.oderId = a.orderid where  sex > '?1' and age between '?2' and '?3' and ((province in ('?4') or sex = '?5') and name like '?6')";
        Map<Integer,Object> params = new HashMap<>();
        params.put(1,"  ");
        params.put(2,"  ");
        params.put(3,"  ");
        //params.put(4,"  ");
        //params.put(5,"  ");
        //params.put(6,"  ");
        long startTime = System.currentTimeMillis();
        System.out.println(ParseUtil.trimSql(sql,params));
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
    }
    /**
     * 测试裁剪SQL
     */
    @Test
    public void testSqltrim3() throws IOException {
        System.out.println("testSqltrim3");
        /*String sql = "SELECT (SELECT SUM(PP.P_A+PP.P_C) FROM (SELECT P_A,P_B,P_C FROM P_TABLE) PP) CUT_A, " +
                "     PRE_A+RPE_B+SUM(PRE_D+PRE_C) SUM_A, PRE_A+RPE_B+PRE_C A_EXP , TT2.A, TT1.B, C, TT2.D ," +
                "     SUB2_D , SUB2_B, F, H, SUB3_C, H3, MAX(H) MX_H FROM TAB_ABCD TT1,TAB_AB TT2,\n" +
                "    (SELECT SUB_D,SUB_B,SUB_C,F FROM TAB_FF) SUB,\n" +
                "    (SELECT SUB2_D,SUB3_C,H ,SUB3_D,SUB3_C,H3 FROM (SELECT SUB2_D,SUB2_B,SUB2_C,H FROM TAB_FFSUB2) SUB2_S ,\n" +
                "    (SELECT SUB3_D,SUB3_B,SUB3_C,H3 FROM TAB_FFSUB3) SUB3_S) SUB2";*/
        String sql = "select * from user u join amount a on u.id = a.pid join goods g on u.id = g.pid and g.oderId = a.orderid where  sex > '?1' and age between '?2' and '?3' and ((province in ('?4') or sex = '?5') and name like '?6')";
        Map<Integer,Object> params = new HashMap<>();
        //params.put(1,"  ");
        //params.put(2,"  ");
        //params.put(3,"  ");
        //params.put(4,"  ");
        //params.put(5,"  ");
        //params.put(6,"  ");
        long startTime = System.currentTimeMillis();
        System.out.println(ParseUtil.trimSql(sql,params));
        System.out.println("总共耗时间："+(System.currentTimeMillis()-startTime)+"ms");
    }

    @Test
    public void ss(){
        String str = "select * from user u join amount a on u.id and a='111' b='?'  and   a  '?' ";
        str= str.replace("'?'", "?");
        System.out.println(str);
    }
}

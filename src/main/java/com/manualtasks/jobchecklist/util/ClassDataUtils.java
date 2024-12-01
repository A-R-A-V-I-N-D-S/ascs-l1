package com.manualtasks.jobchecklist.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ClassDataUtils {

	public ClassDataUtils() {
		super();
	}

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
	
	public static final TimeZone EASTERN_TIME_ZONE = TimeZone.getTimeZone("America/New_York");

	public static final int TOTAL_COL_IN_CHECKLIST_SHEET = 21;

	public static final int TOTAL_COL_IN_CTM_DETAILS_SHEET = 29;

	public static final String CHECKLIST_SHEET_NAME = "Checklist";

	public static final String CTM_DETAILS_SHEET_NAME = "CTM Details";

	public static final String STATUS_ENDED_OK = "Ended OK";

	public static final String STATUS_EXECUTING = "Executing";

	public static final String STATUS_YET_TO_START = "Yet to Start";

	public static final String STATUS_CANCELLED = "Cancelled";

	public static final String STATUS_HELD = "Held";

	public static final String STATUS_NA = "NA";

	public static final int LOG_NAME_START_INDEX1 = 60;

	public static final int LOG_NAME_START_INDEX2 = 56;
	
	

	public static final String[] BATCH_SERVERS_LIST = { "dc04plvbuc300", "dc04plvbuc301", "va10puvbas002" };

	@SuppressWarnings("serial")
	public static final Map<String, String> JOB_NAME_AND_LOG_NAME_MAP = new HashMap<String, String>() {
		{
			// ASCS jobs in 300 & 301 server
			put("LandingZone", "BLCS_PRODUCER_LANDING_ZONE_BATCH_PROD");
			put("autoAppointmentAgentProcess", "ASCS_DAILY_NIPR_VA_REM_AUTO_APPOINTMENT_REQ_PROCESS_PROD");
			put("fetchAgentContactInfoProcess", "ASCS_DAILY_NIPR_PDB_BUMP_CONTACT_INFO_PROCESS_LOAD");
			put("UnappointedProducerNotification", "ASCS_DAILY_UNAPPOINTED_NOTIFICATION_PROCESS_LOAD");
			put("FMOAgentReleaseNotification", "ASCS_DAILY_FMO_MGA_RELEASE_NOTIFICATION_PROCESS_LOAD");
			put("producerCustomPointOutBoundProcess", "BLCS_PRODUCER_SBO_CUSTOM_POINT_OUTBOUND_PROD");
			put("producerRtsProcess", "BLCS_PRODUCER_RTS_PROCESS_BATCH_PROD");
			put("NIPRPDBAlertUpdateLicenseProcess", "ASCS_DAILY_NIPR_PDB_ALERT_UPDATE_AGENT_LICENSE_PROCESS_LOAD");
			put("fetchFailureResponses", "ASCS_DAILY_NIPR_GATEWAY_PROCESS_LOAD");
			put("ContractFormSignCheckJob", "ASCS_DAILY_CONTRACT_FORM_POLLING_PROCESS");
			put("LetterExclusionMassUploadJob", "ASCS_DAILY_LETTER_EXCLUSION_MASS_UPLOAD_PROCESS_PROD");
			put("AgentAppointmentApprovalProcess", "ASCS_DAILY_NIPR_VA_REM_AUTO_APPOINTMENT_RESP_PROCESS_LOAD");
			put("NIPRPDBAlertAppointmentProcess", "ASCS_DAILY_NIPR_PDB_ALERT_UPDATE_AGENT_APPOINTMENT_PROCESS_LOAD");
			put("fetchReportForFailureSubscriptions", "ASCS_DAILY_NIPR_PDB_ALERT_RETRIGGER_REPORT_PROCESS_LOAD");
			put("fetchReportForValidSubscriptions", "ASCS_DAILY_NIPR_PDB_ALERT_TRIGGER_REPORT_PROCESS_LOAD");
			put("NIPRPDBAlertSubscriptionProcess", "ASCS_DAILY_NIPR_PDB_ALERT_SUBSCRIPTION_PROCESS");
			put("NIPRPDBAlertTargetProcess", "ASCS_DAILY_NIPR_PDB_ALERT_TARGET_PROCESS_LOAD");
			put("fetchAgentDemographicsFromNiprAndUpdateToASCS",
					"ASCS_DAILY_NIPR_PDB_ALERT_UPDATE_AGENT_DEMOGRAPHICS_PROCESS_LOAD");
			put("ProducerNiprProcessJob", "ASCS_PRODUCER_NIPR_PROCESS_BATCH_PROD");
			put("niprDailyErrorReportJob", "ASCS_PRODUCER_NIPR_ERROR_REPORT_BATCH_PROD");
			put("ProducerNiprProcessEmailJob", "ASCS_PRODUCER_NIPR_EMAIL_REPORT_BATCH_PROD");
			put("ContractFormAutoSyncJob", "ASCS_DAILY_CONTRACT_FORM_AUTO_SYNC_PROCESS");
			put("ProducerSetupInternalAgentJob", "BLCS_DAILY_INTERNAL_AGENT_SETUP_PROCESS_LOAD");
			put("internalAgentTerminationPSHRJob", "BLCS_DAILY_INTERNAL_AGENT_TERMINATION_PSHR_JOB_PROD");
			put("ProducerSetupPDBBumpTriggerJob", "BLCS_DAILY_AGENT_TRIGGER_PDB_BUMP_PROCESS_LOAD");
			put("ProducerSetupExternalAgentJob", "BLCS_DAILY_EXTERNAL_AGENT_SETUP_PROCESS_LOAD");
			put("ProducerSetupApptResponseJob", "BLCS_DAILY_AGENT_APPT_RESPONSE_PROCESS_LOAD");
			put("ProducerSetupApptRequestJob", "BLCS_DAILY_AGENT_APPT_REQUEST_PROCESS_LOAD");
			put("ContractFormTerminationJob", "ASCS_DAILY_CONTRACT_FORM_TERMINATION_PROCESS");
			put("ProducerSetupStatusUpdateJob", "BLCS_DAILY_PRODUCER_SETUP_STATUS_UPDATE_PROCESS_LOAD");
			put("AgentTermLetterJob", "ASCS_DAILY_TERM_LETTER_PROCESS_LOAD");
			put("WLPSDAuditJob", "ASCS_DAILY_WELCOME_LETTER_AUDIT_PROCESS_LOAD");
			put("VaRemPSDAuditJob", "ASCS_DAILY_VA_REM_AUDIT_PROCESS_LOAD");
			put("WelcomeLetterProcessJob", "ASCS_DAILY_WELCOME_LETTER_PROCESS_LOAD");
			put("LetterProcessJob", "ASCS_DAILY_VA_REMEDIATION_LETTER_PROCESS_LOAD");
			put("ReprocessFailedRecordsJob", "ASCS_DAILY_REPROCESS_FAILED_RECORDS");
			put("CustomPointHandShakeEmailJob", "SBO_DAILY_CP_LETTER_LOAD_PROD");
			put("AgentWelcomeLetterJob", "ASCS_DAILY_AGENT_SETUP_WELCOM_LETTER_PROCESS_LOAD");
			put("ReconReportJob", "ASCS_DAILY_RECON_REPORT_PROCESS_LOAD");
			put("ProducerSetupTriggerBOSJob", "BLCS_DAILY_PRODUCER_SETUP_TRIGGER_BOS_PROCESS_LOAD");
			put("NIPRCARParsingTriggerJob", "BLCS_PRODUCER_NIPR_RECON_PROCESS_PARSING_CAR_LOAD_PRDO");
			put("NIPRALCSCARCompareJob", "ASCS_NIPR_ALCS_CAR_COMPARISION_RECON_PROCESS_LOAD_PROD");
			put("NIPRRetrieveCARTriggerJob", "ASCS_NIPR_CAR_RETRIEVE_PROCESS_PROD");
			put("NIPRSubmitCARTriggerJob", "ASCS_NIPR_CAR_SUBMIT_PROCESS_PROD");
			put("NIPRTOALCSCARReconcileJob", "ASCS_NIPR_APPT_RECONSILE_PROCESS_PROD");
			put("NIPRApptALCSASCSCompareJob", "ALCS_ASCS_APPT_COMPARISION_PROCESS_LOAD_PROD");
			put("LoadFileJob", "BLCS_PRODUCER_FILE_LOADER_BATCH_PROD");
			put("producerOptInProcess", "blcs_producer_optin_process_batch");
			put("IcapApptALCSASCSCompareJob", "ASCS_ICAP_APPOINTMENT_REPORT_PROD");
			put("NIPRToALCSApptSummaryJob", "ASCS_NIPR_ALCS_CAR_SUMMARY_RECON_PROCESS_LOAD_PROD");

			// SBO jobs and report jobs in 002 server
			put("SalesEvent_Hourly_Job", "ASCS_SBO_HOURLY_SALES_EVENT_JOB");
			put("Cmplnt_Mntr_Watchlist_Job", "ASCS_SBO_HOURLY_CMPLT_MNTR_WATCHLIST_DATA_LOAD");
			put("BOD_Mailingservice_Job", "ASCS_SBO_HOURLY_MAILINGSERVICE");
			put("LicenseAppointmentReportJob", "ASCS_SBO_DAILY_LCNS_STATE_APPT_TERM_LOAD");
			put("RTS_Report_Generic_Job", "XXXXXXXXXXXXXXXXXXX");
			put("GenerateRTSReports", "ASCS_SBO_DAILY_GENERATE_RTS_REPORTS");
			put("RTSUniverse", "ASCS_SBO_DAILY_RTS_REPORTS");
			put("RTSCertificationInProgressJob", "ASCS_SBO_DAILY_RTS_CERT_INPROGRESS");
			put("Agent_Retraining_Job", "ASCS_SBO_DAILY_AGENT_RETRAINING");
			put("AgentTypeInfoJob", "BLCS_AGENT_TYPE_INFO_PROD");
			put("SOA_Job", "ASCS_SBO_WEEKLY_SOALOAD_PROCESS");
			put("wcw_infogix_balnchk", "PROD_ASCS_SBO_WCW_FEED_BALNCHK");
			put("WeeklyNYReportJob", "ASCS_WEEKLY_NY_ACTIVE_REPORT_PROD");
			put("AssuranceSubAgentJob", "ASCS_ASSURANCE_SUBAGENT_REPORT_PROD");
			put("AgentAppointmentInvalidDataSubAgentJob", "ASCS_BIWEEKLY_APPT_INVALID_DATA_REPORT_PROD");
			put("EnhanceSubAgentJob", "ASCS_ASSURANCE_SUBAGENT_REPORT_PROD");
			put("Amerilife_Report_Generic_Job", "ASCS_AMERILIFE_SUBAGENT_REPORT_PROD");
			put("AddressLocationReportJob", "ASCS_AGENT_ADDRESS_LOC_REPORT_PROD");
			put("CenterstoneSubAgentJob", "ASCS_CENTERSTONE_SUBAGENT_REPORT_PROD");
			put("ISGSubAgentJob", "ASCS_ISG_SUBAGENT_REPORT_PROD");
			put("IHCSubAgentJob", "ASCS_IHC_SUBAGENT_REPORT_PROD");
			put("AgentPipelineSubAgentJob", "ASCS_AGENT_PIPELINE_SUBAGENT_REPORT_PROD");
			put("EnhanceSubAgentJob", "ASCS_ENHANCE_SUBAGENT_REPORT_PROD");
			put("CaliforniaAgentsJob", "ASCS_CA_AGENTS_REPORT_PROD");
			put("BlcsMonthlyBrokerInfoJob", "ASCS_MONTHLY_BROKER_INFO_PROD");
			put("MonthlyGeorgiaCertifiedAgentJob", "ASCS_MONTHLY_GEORGIA_CERTIFIED_AGENT_REPORT_PROD");
			put("AmericanInsuranceSubAgentJob", "ASCS_AMERICAN_INSURANCE_SUBAGENT_REPORT_PROD");
			put("NAHU_Reporting_Job", "");
		}
	};

	public static final ArrayList<String> ASCSSBO_LOGS_LOCATIONS_300_301 = new ArrayList<String>(
			Arrays.asList("/apps/sclc/batch/producerprecheck/logs/", "/apps/sclc/batch/preprocess/logs/",
					"/apps/sclc/batch/ProducerSboMaintenance/logs/", "/apps/sclc/batch/producerrtsprocess/logs/",
					"/apps/sclc/batch/ProducerSetup/logs/", "/apps/sclc/batch/ProducerEventLoader/logs/",
					"/apps/sclc/batch/producerniprreconprocess/logs/",
					"/apps/sclc/batch/UnappointedProducerProcess/logs/", "/apps/sclc/batch/minmandatory/logs/",
					"/apps/sclc/batch/fileloader/logs/", "/apps/sclc/batch/agentdata/logs/",
					"/apps/sclc/batch/NIPRPDBAlertProcess/logs/", "/apps/sclc/batch/errorreprocess/logs/",
					"/apps/sclc/batch/ProducerNiprProcess/logs/", "/apps/sclc/batch/produceroptinprocess/logs/",
					"/apps/sclc/batch/Producerfileloader/logs/", "/apps/sclc/batch/rts/logs/",
					"/apps/sclc/batch/excelexporter/logs/", "/apps/sclc/batch/producerlanding/logs/",
					"/apps/sclc/batch/Sboarchive/logs/", "/apps/sclc/batch/aceAgentReports/logs/",
					"/apps/sclc/batch/fileextract/logs/", "/apps/sclc/batch/common_config/logs/",
					"/apps/sclc/batch/ProducerSboProcess/logs/", "/apps/sclc/batch/cmdinbound/logs/",
					"/apps/sclc/batch/ProducerLetterProcess/logs/", "/apps/sclc/batch/MassUpload/logs/"));

	public static final ArrayList<String> ASCSSBO_LOGS_LOCATIONS_002 = new ArrayList<String>(
			Arrays.asList("/usr/app/blcs/data_process/logs/", "/usr/app/ascs/data_process/logs/"));
}

package com.marin.qa.selenium.pageObjects.bubble;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Filter extends Bubble{

	private static Filter instance;
	static Logger log = Logger.getLogger(Filter.class);
	 
	/**
	  * Private constructor prevents construction outside this class.
	*/
	protected Filter(){}
	 
	public static synchronized Filter getInstance(){
		
		if (instance == null){
			instance = new Filter();
		}
	 
		return instance;
	}

	/**
	 * Type Enum as list of filter types
	 * @author mmadhusoodan
	 * @version 2.00
	 * @param locator as jQuery mapping for filter Type element
	 */
	public static enum Type {
		
		Date("#dateFilterBubble"),
		Enum("#enumFilterBubble"),
		Num("#numFilterBubble"),
		Text("#textFilterBubble");
		
	   	private String locator;

		private Type(String locator) {
			this.locator = locator;
		}

		public String getLocator() {
			return this.locator;
		}

	}

	/**
	 * Column Enum as list of all column filters
	 * @author mmadhusoodan
	 * @version 2.00
	 * @param type as Enum for filter type
	 * @param locator as jQuery mapping for filter Type element
	 * @param description as description of filter column
	 */
	public static enum Column {
		
		//Activity Log
		ID(Type.Num, "#pfLink_id_display", "[value=\"id_display\"]", "ID"),

		//History Settings
		Date(Type.Date, "#pfLink_transaction_time", "[value=\"transaction_time\"]", "Date"),
		Username(Type.Text, "#pfLink_username", "[value=\"username\"]", "Username"), 
		
		//Operation Column	
		Operation(Type.Enum, "#pfLink_operation_status", "[value=\"operation_status\"]", ""), 

		//Settings
		Account(Type.Text, "#clientaccount_table #pfLink_title, #creative_table #pfLink_client_account", "[value=\"title\"], [value=\"client_account\"]", "Account"), 
		Status(Type.Enum, "#operation_table #pfLink_activity_log_status, #creative_table #pfLink_status, #campaign_table #pfLink_status, #group_table #pfLink_status, #placement_table #pfLink_status", "[value=\"activity_log_status\"], [value=\"status\"]", "Status"), 
		Publisher(Type.Enum, "#pfLink_publisher", "[value=\"publusher\"]", "Publisher"), 
		Campaign(Type.Text, "#campaign_table #pfLink_title, #creative_table #pfLink_campaign", "[value=\"title\"], [value=\"campaign\"]", "Campaign"), 
		Group(Type.Text, "#group_table #pfLink_title, #keyword_table #pfLink_group, #creative_table #pfLink_group", "[value=\"title\"], [value=\"group\"]", "Group"), 
		DestinationURL(Type.Text, "#pfLink_destination_url", "[value=\"destination_url\"]", "Destination URL"), 
		ClickThroughURL(Type.Text, "#pfLink_click_through_url", "[value=\"click_through_url\"]", "Click Through URL"), 
		EndDate(Type.Date, "#pfLink_end_date", "[value=\"end_date\"]", "End Date"),
		Folder(Type.Text, "#folder_table #pfLink_title, #creative_table #pfLink_folder", "[value=\"title\"], [value=\"folder\"]", "Folder"), 
		SearchBid(Type.Num, "#pfLink_max_cpc", "[value=\"max_cpc\"]", "Search Bid $"),
		BidOverride(Type.Enum, "#pfLink_bid_override", "[value=\"bid_override\"]", "Bid Override"),
		OverrideUntil(Type.Date, "#pfLink_override_until", "[value=\"override_until\"]", "Override Until"),
		BidCalcDate(Type.Date, "#pfLink_bid_last_calculated", "[value=\"bid_last_calculated\"]", "Bid Calc. Date"),
		EstimatedRPC(Type.Num, "#pfLink_estimated_rpc", "[value=\"estimated_rpc\"]", "Estimated RPC $"),
		
		//Settings	Channels	
		Channel(Type.Enum, "#pfLink_channel", "[value=\"channel\"]", "Channel"), 

		//Settings	Campaign
		Enhanced(Type.Enum, "#pfLink_enhanced_campaign", "[value=\"enhanced_campaign\"]", "Enhanced"),
		MobileBidAdj(Type.Num, "#pfLink_mobile_bid_modifier", "[value=\"mobile_bid_modifier\"]", "Mobile Bid Adj."),
		FacebookCampaignType(Type.Enum, "#pfLink_facebook_campaign_type", "[value=\"facebook_campaign_type\"]", "Facebook Campaign Type"), 
		LifetimeBudget(Type.Num, "#pfLink_lifetime_budget", "[value=\"lifetime_budget\"]", "Lifetime Budget $"),
		DailyBudget(Type.Num, "#pfLink_daily_budget", "[value=\"daily_budget\"]", "Daily Budget $"),
		Delivery(Type.Enum, "#pfLink_budget_delivery_method", "[value=\"budget_delivery_method\"]", "Delivery"),
		BiddingType(Type.Enum, "#pfLink_bidding_strategy_type", "[value=\"bidding_strategy_type\"]", "Bidding Type"),
		CPCBid(Type.Num, "#pfLink_cpc_bid", "[value=\"cpc_bid\"]", "CPC Bid $"),
        MobileBidAdjExclusion(Type.Enum, "pfLink_mobile_boost_recommendation_override_status", "[value=\"mobile_boost_recommendation_override_status\"]", "Mobile Bid Adj. Exclusion"),
        Objective(Type.Text, "pfLink_objective", "[value=\"objective\"]", "Objective"),
        CreativeRotationThresholdType(Type.Text, "pfLink_dynamic_rotation_threshold_type", "[value=\"dynamic_rotation_threshold_type\"]", "Creative Rotation Threshold Type"),
        CreativeRotationThresholdValue(Type.Num, "pfLink_dynamic_rotation_threshold_value", "[value=\"dynamic_rotation_threshold_value\"]", "Creative Rotation Threshold Value"),
		
		//Settings	Group
		BidType(Type.Text, "#pfLink_bidding_type", "[value=\"bidding_type\"]", "Bid Type"), 
		ContentBid(Type.Num, "#pfLink_recommended_bid", "[value=\"recommended_bid\"]", "Content Bid $"),
		CalcSearchBid(Type.Num, "#pfLink_recommended_bid", "[value=\"recommended_bid\"]", "Calc. Search Bid $"), 
		CalcContentBid(Type.Num, "#pfLink_recommended_content_bid", "[value=\"recommended_content_bid\"]", "Calc. Content Bid $"),
		
		//Settings	Keyword
		Keyword(Type.Text, "#keyword_table #pfLink_title", "[value=\"title\"]", "Keyword"), 
		MatchType(Type.Enum, "#pfLink_type", "[value=\"type\"]", "Match Type"),
		RecSearchBidChg(Type.Num, "#pfLink_recommended_bid_change", "[value=\"recommended_bid_change\"]", "Rec. Search Bid Chg. $"),
		RecSearchBidChgPercent(Type.Num, "#pfLink_recommended_bid_change_percent", "[value=\"recommended_bid_change_percent\"]", "Rec. Search Bid Chg. %"),
		MinBid(Type.Num, "#pfLink_min_cpc", "[value=\"min_cpc\"]", "Min Bid $"),
		MinBidDelta(Type.Num, "#pfLink_min_bid_delta", "[value=\"min_bid_delta\"]", "Min Bid Delta $"),
		GroupSearchBid(Type.Num, "#pfLink_group_bid", "[value=\"group_bid\"]", "Group Search Bid $"),
		CalcGroupSearchBid(Type.Num, "#pfLink_recommended_group_bid", "[value=\"recommended_group_bid\"]", "Calc. Group Search Bid $"),
		QualityScore(Type.Num, "#pfLink_quality_score", "[value=\"quality_score\"]", "Quality Score"),

		//Settings	Creative
		Headline(Type.Text, "#pfLink_title", "[value=\"title\"]", "Headline"), 
        Creative(Type.Text, "#pfLink_facebook_creative_name", "[value=\"facebook_creative_name\"]", "Creative"), 
		DescriptionLine1(Type.Text, "#pfLink_desc1", "[value=\"desc1\"]", "Description Line 1"), 
		DescriptionLine2(Type.Text, "#pfLink_desc2", "[value=\"desc2\"]", "Description Line 2"), 
		DisplayURL(Type.Text, "#pfLink_display_url", "[value=\"display_url\"]", "Display URL"), 
		CreativeType(Type.Enum, "#pfLink_creative_type", "[value=\"creative_type\"]", "Creative Type"),
		DevicePreference(Type.Enum, "#pfLink_mobile_preferred", "[value=\"mobile_preferred\"]", "Device Preference"),
		RelatedPage(Type.Enum, "#pfLink_related_page_settings", "[value=\"related_page_settings\"]",  "Related Page"),
		OffsitePixels(Type.Text, "#pfLink_conversion_pixels", "[value=\"conversion_pixels\"]", "Offsite Pixels"), 
		RotationType(Type.Enum, "#pfLink_rotation_type", "[value=\"rotation_type\"]", "Rotation Type"),
		FacebookCreativeType(Type.Enum, "#pfLink_facebook_creative_type", "[value=\"facebook_creative_type\"]", "Facebook Creative Type"), 
        CreativeObjective(Type.Text, "#pfLink_facebook_creative_objective", "[value=\"facebook_creative_objective\"]", "Creative Objective"),
        PagePostID(Type.Text, "#pfLink_page_post_id", "[value=\"page_post_id\"]", "Page Post ID"),
        PagePostType(Type.Text, "#pfLink_page_post_type", "[value=\"page_post_type\"]", "Page Post Type"),
        FacebookAppType(Type.Text, "#pfLink_facebook_app_platform_type", "[value=\"facebook_app_platform_type\"]", "Facebook App Type"),
		RelatedPageName(Type.Text, "#pfLink_related_page_name", "[value=\"related_page_name\"]", "Related Page Name"), 
		DestinationConnection(Type.Text, "#pfLink_destination_connection", "[value=\"destination_connection\"]", "Destination Connection"), 
		OptionalURLTags(Type.Text, "#pfLink_url_tags", "[value=\"url_tags\"]", "Optional URL Tags"), 
        CallToActionType(Type.Enum, "#pfLink_facebook_call_to_action_type", "[value=\"facebook_call_to_action_type\"]", "Call To Action Type"),
        LinkUrl(Type.Text, "#pfLink_link_url", "[value=\"link_url\"]", "Link Url"),
        DeepLinkUrl(Type.Text, "#pfLink_deep_link_url", "[value=\"deep_link_url\"]", "Deep Link Url"),

		//Settings	Placements
		Placement(Type.Text, "#placement_table #pfLink_title", "[value=\"title\"]",  "Placement"), 
		MaxBid(Type.Num, "#pfLink_max_cpc", "[value=\"max_cpc\"]", "Max Bid $"),
		MaxCPMBid(Type.Num, "#pfLink_max_cpm", "[value=\"max_cpm\"]", "Max CPM Bid $"),
		GroupContentBid(Type.Num, "#pfLink_recommended_bid", "[value=\"recommended_bid\"]", "Group Content Bid $"),
		GroupContentCPMBid(Type.Num, "#pfLink_group_cpm_bid", "[value=\"group_cpm_bid\"]", "Group Content CPM Bid $"),
		CalcBid(Type.Num, "#pfLink_recommended_bid", "[value=\"recommended_bid\"]", "Calc. Bid $"),

		//Settings	Bidding

		//Settings	Segments

		//Settings	Templates
		Template(Type.Text, "#targettemplate_table #pfLink_title", "[value=\"title\"]", "Template"), 
		
		//Traffic Coast
		Impressions(Type.Num, "#pfLink_impressions", "[value=\"impressions\"]", ""),
		Clicks(Type.Num, "#pfLink_pub_clicks", "[value=\"pub_clicks\"]", ""),
		CTR(Type.Num, "#pfLink_ctr", "[value=\"ctr\"]", ""),
		PubCost(Type.Num, "#pfLink_pub_cost", "[value=\"pub_cost\"]", ""),
		AvgCPC(Type.Num, "#pfLink_avg_cpc", "[value=\"avg_cpc\"]", ""),
		AvgBid(Type.Num, "#pfLink_avg_bid", "[value=\"avg_bid\"]", ""),
		Headroom(Type.Num, "#pfLink_headroom", "[value=\"headroom\"]", ""),
		AvgPos(Type.Num, "#pfLink_avg_position", "[value=\"avg_position\"]", ""),
		eCPM(Type.Num, "#pfLink_ecpm", "[value=\"ecpm\"]", ""),

		//Social
		SocialImpr(Type.Num, "#pfLink_impressions_-1", "[value=\"impressions_-1\"]", ""),
		Social(Type.Num, "#pfLink_impression_percentage_-1", "[value=\"impression_percentage_-1\"]", ""),
		SocialClicks(Type.Num, "#pfLink_pub_clicks_-1", "[value=\"pub_clicks_-1\"]", ""),
		SocialCTR(Type.Num, "#pfLink_ctr_-1", "[value=\"ctr_-1\"]", ""),
		Reach(Type.Num, "#pfLink_impressions_-2", "[value=\"impressions_-2\"]", ""),
		UniqueClicks(Type.Num, "#pfLink_pub_clicks_-2", "[value=\"pub_clicks_-2\"]", ""),
		UniqueCTR(Type.Num, "#pfLink_ctr_-2", "[value=\"ctr_-2\"]", ""),
		Frequency(Type.Num, "#pfLink_impression_ratio_-2", "[value=\"impression_ratio_-2\"]", ""),
		SocialReach(Type.Num, "#pfLink_impressions_-3", "[value=\"impressions_-3\"]", ""),
		SocialUniqueClicks(Type.Num, "#pfLink_pub_clicks_-3", "[value=\"pub_clicks_-3\"]", ""),
		SocialUniqueCTR(Type.Num, "#pfLink_ctr_-3", "[value=\"ctr_-3\"]", ""),
		NewsfeedImpr(Type.Num, "#pfLink_impressions_-4", "[value=\"impressions_-4\"]", ""),
		NewsfeedClicks(Type.Num, "#pfLink_pub_clicks_-4", "[value=\"pub_clicks_-4\"]", ""),
		NewsfeedCTR(Type.Num, "#pfLink_ctr_-4", "[value=\"ctr_-4\"]", ""),
		NewsfeedAvgPos(Type.Num, "#pfLink_avg_position_-4", "[value=\"avg_position_-4\"]", ""),

		//Page Place Actions
		PageLikesClicks(Type.Num, "#pfLink_conversions_-103", "[value=\"conversions_-103\"]", ""),
		CheckinClicks(Type.Num, "#pfLink_conversions_-104", "[value=\"conversions_-104\"]", ""),
		PhotoTagClicks(Type.Num, "#pfLink_conversions_-106", "[value=\"conversions_-106\"]", ""),
		MentionsInPageClicks(Type.Num, "#pfLink_conversions_-105", "[value=\"conversions_-105\"]", ""),
		PageLikesImpr(Type.Num, "#pfLink_conversions_-121", "[value=\"conversions_-121\"]", ""),
		CheckinImpr(Type.Num, "#pfLink_conversions_-122", "[value=\"conversions_-122\"]", ""),
		MentionsInPageImpr(Type.Num, "#pfLink_conversions_-123", "[value=\"conversions_-123\"]", ""),
		PhotoTagImpr(Type.Num, "#pfLink_conversions_-124", "[value=\"conversions_-124\"]", ""),

		//Page Place Post Actions
		PagePostLikesClicks(Type.Num, "#pfLink_conversions_-112", "[value=\"conversions_-112\"]", ""),
		CommentsOnPageClicks(Type.Num, "#pfLink_conversions_-107", "[value=\"conversions_-107\"]", ""),
		PagePostSharesClicks(Type.Num, "#pfLink_conversions_-108", "[value=\"conversions_-108\"]", ""),
		LinkClicksClicks(Type.Num, "#pfLink_conversions_-111", "[value=\"conversions_-111\"]", ""),
		PagePhotoViewsClicks(Type.Num, "#pfLink_conversions_-109", "[value=\"conversions_-109\"]", ""),
		VideoPlaysClicks(Type.Num, "#pfLink_conversions_-110", "[value=\"conversions_-110\"]", ""),
		PagePostLikesImpr(Type.Num, "#pfLink_conversions_-130", "[value=\"conversions_-130\"]", ""),
		CommentsOnPageImpr(Type.Num, "#pfLink_conversions_-125", "[value=\"conversions_-125\"]", ""),
		PagePostSharesImpr(Type.Num, "#pfLink_conversions_-126", "[value=\"conversions_-126\"]", ""),
		LinkClicksImpr(Type.Num, "#pfLink_conversions_-129", "[value=\"conversions_-129\"]", ""),
		PagePhotoViewsImpr(Type.Num, "#pfLink_conversions_-127", "[value=\"conversions_-127\"]", ""),
		VideoPlaysImpr(Type.Num, "#pfLink_conversions_-128", "[value=\"conversions_-128\"]", ""),

		//Applications
		AppInstallsClicks(Type.Num, "#pfLink_conversions_-117", "[value=\"conversions_-117\"]", ""),
		AppUsesClicks(Type.Num, "#pfLink_conversions_-116", "[value=\"conversions_-116\"]", ""),
		PlayingClicks(Type.Num, "#pfLink_conversions_-115", "[value=\"conversions_-115\"]", ""),
		SpentClicks(Type.Num, "#pfLink_conversions_-118", "[value=\"conversions_-118\"]", ""),
		AppInstallsImpr(Type.Num, "#pfLink_conversions_-135", "[value=\"conversions_-135\"]", ""),
		AppUsesImpr(Type.Num, "#pfLink_conversions_-134", "[value=\"conversions_-134\"]", ""),
		SpentImpr(Type.Num, "#pfLink_conversions_-136", "[value=\"conversions_-136\"]", ""),
		PlayingImpr(Type.Num, "#pfLink_conversions_-133", "[value=\"conversions_-133\"]", ""),

		//Events
		RSVPClicks(Type.Num, "#pfLink_conversions_-102", "[value=\"conversions_-102\"]", ""),
		RSVPImpr(Type.Num, "#pfLink_conversions_-120", "[value=\"conversions_-120\"]", ""),

		//Conversions Revenue
		Conversions(Type.Num, "#pfLink_conversions", "[value=\"conversions\"]", ""),
		ConvRate(Type.Num, "#pfLink_conversion_rate", "[value=\"conversion_rate\"]", ""),
		CostConv(Type.Num, "#pfLink_cost_per_conversion", "[value=\"cost_per_conversion\"]", ""),
		Revenue(Type.Num, "#pfLink_revenue", "[value=\"revenue\"]", ""),
		RevConv(Type.Num, "#pfLink_revenue_per_conversion", "[value=\"revenue_per_conversion\"]", ""),
		RevClick(Type.Num, "#pfLink_revenue_per_click", "[value=\"revenue_per_click\"]", ""),
		ROAS(Type.Num, "#pfLink_roas", "[value=\"roas\"]", ""),

		//Gross Profit
		GrossProfit(Type.Num, "#pfLink_gross_profit", "[value=\"gross_profit\"]", ""),
		GPConv(Type.Num, "#pfLink_gross_profit_per_conversion", "[value=\"gross_profit_per_conversion\"]", ""),
		GPClick(Type.Num, "#pfLink_gross_profit_per_click", "[value=\"gross_profit_per_click\"]", ""),
		GPOAS(Type.Num, "#pfLink_gpoas", "[value=\"gpoas\"]", ""),
		
		//Profit
		Profit(Type.Num, "#pfLink_profit", "[value=\"profit\"]", ""),
		Margin(Type.Num, "#pfLink_profit_margin", "[value=\"profit_margin\"]", ""),
		ProfitClick(Type.Num, "#pfLink_profit_per_click", "[value=\"profit_per_click\"]", ""),
		ROI(Type.Num, "#pfLink_roi", "[value=\"roi\"]", ""),
		
		//Targets Creative
		AudienceSize(Type.Num, "#pfLink_audience_size", "[value=\"audience_size\"]", ""),
		PubMinSugCPC(Type.Num, "#pfLink_min_suggested_cpc", "[value=\"min_suggested_cpc\"]", ""),
		PubMaxSugCPC(Type.Num, "#pfLink_max_suggested_cpc", "[value=\"max_suggested_cpc\"]", ""),
		PubMinSugCPM(Type.Num, "#pfLink_min_suggested_cpm", "[value=\"min_suggested_cpm\"]", ""),
		PubMaxSugCPM(Type.Num, "#pfLink_max_suggested_cpm", "[value=\"max_suggested_cpm\"]", ""),

		//Active Log		
		Description(Type.Text, "#pfLink_description", "[value=\"description\"]", "Description"), 
		User(Type.Text, "#pfLink_user", "[value=\"user\"]", "User"), 

		//Dimensions
		Segments(Type.Text, "#metadata_table #pfLink_title, #pfLink_metadata_1010969", "[value=\"title\"], [value=\"metadata_1010969\"]", ""), 

		//Targets
		Colleges(Type.Text, "#pfLink_colleges", "[value=\"colleges\"]", "Colleges"), 
		CollegeMajors(Type.Text, "#pfLink_college_majors", "[value=\"college_majors\"]", "College Majors"), 
		GraduationYears(Type.Text, "#pfLink_graduating_years", "[value=\"graduating_years\"]", "Graduation Years"), 
		Languages(Type.Text, "#pfLink_languages", "[value=\"languages\"]", "Languages"), 
		Workplaces(Type.Text, "#pfLink_companies", "[value=\"companies\"]", "Workplaces"), 
		InterestedIn(Type.Text, "#pfLink_languages", "[value=\"languages\"]", "Interested In"), 
		Likes(Type.Text, "#pfLink_keywords", "[value=\"keywords\"]", "Likes"), 
		BroadCategories(Type.Text, "#pfLink_broad_categories", "[value=\"broad_categories\"]", "Broad Categories"), 
		Countries(Type.Text, "#pfLink_countries", "[value=\"countries\"]", "Countries"), 
		States(Type.Text, "#pfLink_states", "[value=\"states\"]", "States"), 
		Cities(Type.Text, "#pfLink_cities", "[value=\"cities\"]", "Cities"), 
		ZipCodes(Type.Text, "#pfLink_zip_codes", "[value=\"zip_codes\"]", "Zip Codes"), 
		TargetedEntities(Type.Text, "#pfLink_targeted_entities", "[value=\"targeted_entities\"]", "Targeted Entities"), 
		Connections(Type.Text, "#pfLink_connections", "[value=\"connections\"]", ""), 
		NotConnections(Type.Text, "#pfLink_not_connections", "[value=\"not_connections\"]", "Excluded Connections"), 
		FriendConnections(Type.Text, "#pfLink_friend_connections", "[value=\"friend_connections\"]", "Friend Connections"),

		//Advanced
		ABTest(Type.Enum, "#pfLink_judgment", "[value=\"judgment\"]", "A/B Test"),
		BiddingCategory(Type.Enum, "#pfLink_bid_performance_category", "[value=\"bid_performance_category\"]", "Bidding Category"),
		CreationDate(Type.Date, "#pfLink_creation_date", "[value=\"creation_date\"]", "Creation Date"),
		SchedAction(Type.Date, "#pfLink_next_scheduled_action_date", "[value=\"next_scheduled_action_date\"]", "Scheduled Action"),
		AdName(Type.Text, "#pfLink_ad_name", "[value=\"ad_name\"]", "Ad Name"),
		Name(Type.Text, "#pfLink_facebook_ad_name", "[value=\"facebook_ad_name\"]", "Name"),
		PubID(Type.Text, "#pfLink_ext_id", "[value=\"ext_id\"]", "Public ID"),
		Image(Type.Text, "#pfLink_image_url", "[value=\"image_url\"]", "Image"),
		ImageSize(Type.Text, "#pfLink_image_dimensions", "[value=\"image_dimensions\"]", "Image Size"),
		UniqueID(Type.Text, "#pfLink_uniq_id", "[value=\"uniq_id\"]", "Unique ID"),
		BaiduStatus(Type.Text, "#pfLink_baidu_status", "[value=\"baidu_status\"]", "Baidu Status"),
		
		//Image Library 
		ImageName(Type.Text, "#pfLink_name", "[value=\"name\"]", "Image Name");

		private Type type;
	   	private String locator;
	   	private String value;
	   	private String filterList = "#filterList ";
	   	private String spinner = ".grid_overlay";
		private String description;
		
		private Column(Type type, String locator, String value, String description) {
			this.type = type;
			this.locator = locator;
			this.value = value;
			this.description = description;
		}

		public Type getType() {
			return this.type;
		}

		public String getLocator() {
			return this.locator;
		}

		public String getValue() {
			return this.value;
		}
		
		public String getFilterList() {
			return this.filterList;
		}
		
		public String getSpinner() {
			return this.spinner;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}
	
	/**
	 * Drop Down Menu Element as list of all drop down menu options in filter bubble
	 * @version 2.00
	 * @param value as jQuery mapping for menu option element value
	 * @author mmadhusoodan
	 */
	public static enum Menu {
		
		Equals("Equals"), 
		DoesNotEqual("DoesNotEqual"), 
		GreaterThan("GreaterThan"), 
		GreaterThanOrEqualTo("GreaterThanOrEqualTo"), 
		LessThan("LessThan"), 
		LessThanOrEqualTo("LessThanOrEqualTo"), 
		Between("Between"),
		Contains("Contains"), 
		DoesntContain("DoesntContain"), 
		IsBlank("IsBlank"), 
		IsNotBlank("IsNotBlank"),
		On("On"), 
		OnOrAfter("OnOrAfter"), 
		OnOrBefore("OnOrBefore");
		
	   	private String value;

		private Menu(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

	/**
	 * NumMenu Element as DropDown Menu for Num Filter
	 * @version 2.00
	 * @param locator as jQuery mapping for filter NumMenu element
	 * @param value as jQuery mapping for menu option element value
	 * @param description as description of Num Filter DropDown Menu
	 * @author mmadhusoodan
	 */
	public static enum NumMenu {
		
		Equals(".num_filter_operator", "eq", "Equals"), 
		DoesNotEqual(".num_filter_operator", "noteq", "Does not equal"), 
		GreaterThan(".num_filter_operator", "greater", "Greater than"), 
		GreaterThanOrEqualTo(".num_filter_operator", "greatereq", "Greater than or equal to"), 
		LessThan(".num_filter_operator", "less", "Less than"), 
		LessThanOrEqualTo(".num_filter_operator", "lesseq", "Less than or equal to"), 
		Between(".num_filter_operator", "between", "Between");
		
	   	private String locator;
	   	private String value;
		private String description;

		private NumMenu(String locator, String value, String description) {
			this.locator = locator;
			this.value = value;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		public String getValue() {
			return this.value;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}

	/**
	 * TextMenu Element as DropDown Menu for Text Filter
	 * @version 2.00
	 * @param locator as jQuery mapping for filter TextMenu element
	 * @param value as jQuery mapping for menu option element value
	 * @param description as description of Text Filter DropDown Menu
	 * @author mmadhusoodan
	 */
	public static enum TextMenu {
		
		Contains(".text_filter_operator", "contains", "Contains"), 
		Equals(".text_filter_operator", "equals", "Equals"), 
		DoesntContain(".text_filter_operator", "notcontains", "Doesn't contain"), 
		IsBlank(".text_filter_operator", "isblank", "Is blank"), 
		IsNotBlank(".text_filter_operator", "isnotblank", "Is not blank");
		
	   	private String locator;
	   	private String value;
		private String description;

		private TextMenu(String locator, String value, String description) {
			this.locator = locator;
			this.value = value;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		public String getValue() {
			return this.value;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}

	/**
	 * DateMenu Element as DropDown Menu for Date Filter
	 * @version 2.00
	 * @param locator as jQuery mapping for filter DateMenu element
	 * @param value as jQuery mapping for menu option element value
	 * @param description as description of Date Filter DropDown Menu
	 * @author mmadhusoodan
	 */
	public static enum DateMenu {
		
		On(".date_filter_operator", "eq", "On"), 
		OnOrAfter(".date_filter_operator", "greatereq", "On or after"), 
		OnOrBefore(".date_filter_operator", "lesseq", "On or before"), 
		Between(".date_filter_operator", "between", "Between");
		
	   	private String locator;
	   	private String value;
		private String description;

		private DateMenu(String locator, String value, String description) {
			this.locator = locator;
			this.value = value;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		public String getValue() {
			return this.value;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}

	/**
	 * Radio Element as list of all radio buttons in filter
	 * @version 2.00
	 * @param locator as jQuery mapping for Radio Button element locator 
	 * @param value as jQuery mapping for radio button value
	 * @param description as description of Radio button
	 * @author mmadhusoodan
	 */
	public static enum Radio {

		//Operation
		Synced("[name=\"operation_status\"]", "SYNCED", "Synced"), 
		Syncing("[name=\"operation_status\"]", "SYNCING,RESYNCING,RESYNCING_FROM_REPORT", "Syncing"),
		NeedsSync("[name=\"operation_status\"]", "SYNC_ERROR,CHILD_SYNC_ERROR", "NeedsSync"),
		SyncError("[name=\"operation_status\"]", "INVALID", "SyncError"), 
		Editing("[name=\"operation_status\"]", "EDITING,ADDING,DELETING,MODIFY_STATUS,EDITING_REDIRECT,EDITING_AND_GEO,BIDDING", "Editing"), 
	
		//Status
		AllButDeleted("[name=\"status\"]", "ALLBUTDELETED", "All but Deleted"), 
		Active("[name=\"status\"]", "ACTIVE", "Active"),
		Paused("[name=\"status\"]", "PAUSED", "Paused"),
		Disapproved("[name=\"status\"]", "DISAPPROVED", "Disapproved"), 
		Deleted("[name=\"status\"]", "DELETED", "Deleted"); 
		
	   	private String locator;
	   	private String value;
		private String description;

		private Radio(String locator, String value, String description) {
			this.locator = locator;
			this.value = value;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		public String getValue() {
			return this.value;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}

	/**
	 * CheckBox Element as list of all check boxes within Enum filter
	 * @version 2.00
	 * @param locator as jQuery mapping for Check Box element locator 
	 * @param description as description for CheckBox element
	 * @author mmadhusoodan
	 */
	public static enum CheckBox {

		//Status
		ToBeSent("[name=\"PENDING,SUBMITTED,IN_PROGRESS,NEEDS_RESUBMIT\"]", "To be sent"),
		SucceededPartially("[name=\"PARTIAL_SUCCESS\"]", "Succeeded Partially"),
		Failed("[name=\"FAILED\"]", "Failed"),
		Succeeded("[name=\"COMPLETED\"]", "Succeeded"),
		Canceled("[name=\"CANCELED\"]", "Canceled"),
		Held("[name=\"PAUSED\"]", "Held"), 

		//Publisher
		PublisherNone("[name=\"-1\"]", "None"),
		Generic("[name=\"2\"]", "Generic"),
		Google("[name=\"4\"]", "Google"), 
		Yahoo("[name=\"5\"]", "Yahoo"),
		Bing("[name=\"6\"]", "Bing"),
		Facebook("[name=\"7\"]", "Facebook"), 
		Baidu("[name=\"8\"]", "Baidu"), 
		YahooJapan("[name=\"9\"]", "Yahoo Japan"),
		Atlas("[name=\"10\"]", "Atlas"),
		Criteo("[name=\"11\"]", "Criteo"),
		
		//Campaign Enhanced
		Legacy("[name=\"LEGACY\"]", "Legacy"),
		Enhanced("[name=\"ENHANCED\"]", "Enhanced"), 

		//Campaign Delivery
		Standard("[name=\"STANDARD\"]", "Standard"),
		Accelerated("[name=\"ACCELERATED\"]", "Accelerated"), 

		//Related Page
		On("[name=\"ON\"]", "On"),
		Off("[name=\"OFF\"]", "Off"), 
		
		//Rotation Type
		Dynamic("[name=\"DYNAMIC\"]", "Dynamic"),
		Manual("[name=\"MANUAL\"]", "Manual"), 
		
		//Bidding Type
		BudgetOptimizer("[name=\"BudgetOptimizer\"]", "Budget Optimizer"),
		ConversionOptimizer("[name=\"ConversionOptimizer\"]", "Conversion Optimizer"), 
		CPC("[name=\"ManualCpc\"]", "CPC"),
		CPM("[name=\"ManualCpm\"]", "CPM"), 

		//Match Type
		Broad("[name=\"BROAD\"]", "Broad"),
		Content("[name=\"CONTENT\"]", "Content"), 
		Exact("[name=\"EXACT\"]", "Exact"),
		Phrase("[name=\"PHRASE\"]", "Phrase"), 
		
		//Bid Overrride
		Position("[name=\"POSITION\"]", "Position"), 
		
		//Facebook Campaign Type
		FixedPriceCampaign("[name=\"HOMEPAGE\"]", "Fixed Price Campaign"), 
		AuctionCampaign("[name=\"MARKETPLACE\"]", "Auction Campaign"), 

		//Facebook Creative Type
		OffsiteAd("[name=\"STANDARD_AD\"]", "Offsite Ad"), 
		PageLikeAd("[name=\"INLINE_LIKE\"]", "Page Like Ad"), 
		PagePostAd("[name=\"PAGE_POST_AD\"]", "Page Post Ad"),
		AppAd("[name=\"APP_AD\"]", "App Ad"),
		EventAd("[name=\"EVENT_RSVP\"]", "Event Ad"), 
		QuestionAd("[name=\"QUESTION_AD\"]", "Question Ad"), 
		OfferAd("[name=\"OFFER_AD\"]", "Offer Ad"),
		MobileAppInstallAd("[name=\"MOBILE_APPLICATION_INSTALL_AD\"]", "Mobile App Install Ad"), 
		
        //Facebook Objective
        None("[name=\"NONE\"]", "None"), 
        OfferClaims("[name=\"OFFER_CLAIMS\"]", "Offer Claims"), 
        PageLikes("[name=\"PAGE_LIKES\"]", "Page Likes"),
        AppInstalls("[name=\"CANVAS_APP_INSTALLS\"]", "App Installs"),
        AppEngagement("[name=\"CANVAS_APP_ENGAGEMENT\"]", "App Engagement"), 
        EventResponses("[name=\"EVENT_RESPONSES\"]", "Event Responses"), 
        PagePostEngagement("[name=\"POST_ENGAGEMENT\"]", "Page Post Engagement"),
        WebsiteConversions("[name=\"WEBSITE_CONVERSIONS\"]", "Website Conversions"), 
        MobileAppInstalls("[name=\"MOBILE_APP_INSTALLS\"]", "Mobile App Installs"), 
        MobileAppEngagement("[name=\"MOBILE_APP_ENGAGEMENT\"]", "Mobile App Engagement"),
        ClicksToWebsite("[name=\"WEBSITE_CLICKS\"]", "Clicks to Website"), 

        //Bidding Category
        OnBidOverride("[name=\"BID_OVERRIDE\"]", "On Bid Override"), 
        OnPositionOverride("[name=\"BID_TO_POSITION\"]", "On Position Override"), 
        RaisedToMinBid ("[name=\"FORCED_ABOVE_MIN_BID\"]", "Raised to Min Bid"),
        AtBidFloor("[name=\"BID_FLOOR_ENFORCED\"]", "At Bid Floor"),
        AtBidCap("[name=\"BID_CAP_ENFORCED\"]", "At Bid Cap"), 
        BidToStretchTarget("[name=\"STRETCHED_TO_MIN_BID\"]", "Bid to Stretch Target"), 
        AbovePositionCap("[name=\"POSITION_CAP_ENFORCED\"]", "Above Position Cap"),
        Economic("[name=\"BID_AT_MARKET\"]", "Economic"), 
        BidToPosition("[name=\"POSITION_BASED_BID\"]", "Bid to Position"), 

        //Creative Type
		Text("[name=\"TEXT\"]", "Text"),
        Text2("[name=\"TEXT2\"]", "Text2"),
		Image("[name=\"IMAGE\"]", "Image"),
		Video("[name=\"VIDEO\"]", "Video"),
		Mobile("[name=\"MOBILE\"]", "Mobile"), 
		MobileImage("[name=\"MOBILE_IMAGE\"]", "Mobile Image"),
		Product("[name=\"PRODUCT\"]", "Product"),
		LocalBusiness("[name=\"LOCAL_BUSINESS\"]", "Local Business"), 
		Deprecated("[name=\"DEPRECATED\"]", "Deprecated"), 
		Template("[name=\"TEMPLATE\"]", "Template"), 

		//A/B Test
		Winner("[name=\"winer\"]", "Winner"), 
		Loser("[name=\"loser\"]", "Loser"),
		Draw("[name=\"draw\"]", "Draw"), 

		//Channel
		Affiliate("[name=\"AFFILIATE\"]", "Affiliate"), 
		ComparisonShopping("[name=\"COMPARISON_SHOPPING\"]", "Comparison Shopping"), 
		DirectMail("[name=\"DIRECT_MAIL\"]", "Direct Mail"), 
		DirectUnknown("[name=\"DIRECT\"]", "Direct Unknown"),
		Display("[name=\"DISPLAY\"]", "Display"), 
		Email("[name=\"EMAIL\"]", "Email"), 
		LocalDirectory("[name=\"LOCAL_DIRECTORY\"]", "Local Directory"),
		Offline("[name=\"OFFLINE\"]", "Offline"), 
		OrganicSearch("[name=\"NATURAL_SEARCH\"]", "Organic Search"),
		PaidSearch("[name=\"PAID_SEARCH\"]", "Paid Search"),
		Radio("[name=\"RADIO\"]", "Radio"),
		Retargeting("[name=\"RETARGETING\"]", "Retargeting"),
		Social("[name=\"SOCIAL\"]", "Social"),
		TV("[name=\"TV\"]", "TV"),
		Other("[name=\"OTHER\"]", "Other");
		
	   	private String locator;
		private String description;


		private CheckBox(String locator, String description) {
			this.locator = locator;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}
	
	/**
	 * Text Input Element as list of all text inputs in filter 
	 * @version 2.00
	 * @param locator as jQuery mapping for Text Input element locator 
	 * @param description as description for Text Input element
	 * @author mmadhusoodan
	 */
	public static enum Input {
		
		Date("[name=\"dateFilterValue\"]", "Date"),
		DateBetween("[name=\"dateFilterLowValue\"]", "Between Date"),
		DateAnd("[name=\"dateFilterHighValue\"]", "And Date"),
		Num("[name=\"numFilterValue\"]", "Number"),
		NumBetween("[name=\"numFilterLowValue\"]", "Between Number"),
		NumAnd("[name=\"numFilterHighValue\"]", "And Number"),
		Text("[name=\"textFilterValue\"]", "Text");

	   	private String locator;
		private String description;

		private Input(String locator, String description) {
			this.locator = locator;
			this.description = description;
		}

		public String getLocator() {
			return this.locator;
		}

		@Override
	    public String toString() {
	        return description;
	    }

	}

	/**
	 * Button as list of all buttons in Date Filter
	 * @version 2.00
	 * @param locator as jQuery mapping for filter button
	 * @author mmadhusoodan
	 */
    public static enum Button {

        Apply("[name=\"apply\"]", ".grid_overlay"), Clear("[name=\"clear\"]", null);

        private String locator;
        private String spinner;

        private Button(String locator, String spinner) {
            this.locator = locator;
            this.spinner = spinner;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getSpinner() {
            return this.spinner;
        }

    }

    public void apply(WebDriver driver, Column column, Menu menu, String text) {
        switch (column.getType()) {
            case Text:
                click(driver, column).select(driver, TextMenu.valueOf(menu.getValue())).type(driver, Input.Text, text).click(driver, Button.Apply);
            break;
            default:

            break;
        }
    }

    /**
     * This Method set to open filter on Column
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param column
     * @return Filter
     * 
     */
    public Filter click(WebDriver driver, Column column) {
        String query = "$('" + column.getLocator() + "').click();";
        if (isElementPresent(driver, column.getLocator())) {
            changeElementBackground(driver, column.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(400);
            waitForElementToBeVisible(driver, column.getType().getLocator());
            log.info("Open \"" + column.toString() + "\" Filter in \"" + this.getClass().getSimpleName() + "\"");
            removeElementBackground(driver, column.getLocator());
        }

        return instance;

    }

    /**
     * This Method set to click the button
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param column
     * @return Filter
     * 
     */
    public Filter click(WebDriver driver, Button button) {
        String query = "$('" + button.getLocator() + "').click();";
        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(700);
            log.info("Open \"" + button.toString() + "\" Filter in \"" + this.getClass().getSimpleName() + "\"");
            removeElementBackground(driver, button.getLocator());
        }

        return instance;

    }

    /**
     * This Method set to select option on drop down menu on Text Filter
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param menu
     * @return Filter
     * 
     */
    public Filter select(WebDriver driver, TextMenu menu) {

        String query = "$('" + menu.getLocator() + "').val('" + menu.getValue() + "').change();";
        if (isElementPresent(driver, menu.getLocator()) == true) {
            changeElementBackground(driver, menu.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            log.info("Selected \"" + menu.toString() + "\" Menu Option in \"" + this.getClass().getSimpleName() + "\"");
            removeElementBackground(driver, menu.getLocator());
        }

        return instance;
    }

    /**
     * This Method set to type value in between inputs
     * 
     * @author mbeider
     * @param selenium
     * @param input
     * @param between
     * @param and
     * @return Filter
     * 
     */
    public Filter type(WebDriver driver, Input input, String option) {

        String query = "$('" + input.getLocator() + "').val('" + option + "');";
        if (isElementPresent(driver, input.getLocator()) == true) {
            changeElementBackground(driver, input.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            log.info("Type \"" + option + "\" in \"" + input.toString() + "\" Text Input in \"" + this.getClass().getSimpleName() + "\"");
            removeElementBackground(driver, input.getLocator());
        }

        return instance;

    }

        
}

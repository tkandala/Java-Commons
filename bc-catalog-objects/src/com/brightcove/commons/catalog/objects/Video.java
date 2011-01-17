package com.brightcove.commons.catalog.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.brightcove.commons.catalog.objects.enumerations.EconomicsEnum;
import com.brightcove.commons.catalog.objects.enumerations.GeoFilterCodeEnum;
import com.brightcove.commons.catalog.objects.enumerations.ItemStateEnum;

/**
 * <p>
 *    A Video object (the metadata defining a video).
 * </p>
 * 
 * <p>
 *    This object is defined primarily by the Media API documentation for
 *    a Video object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Video">http://support.brightcove.com/en/docs/media-api-objects-reference#Video</a>.
 * </p>
 * <p>
 *    This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * <p>
 *     <code>
 *           The Video object is an aggregate of metadata and asset information associated with a video.
 *     </code>
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class Video {
	private String  name;
	private Long    id;
	private String  referenceId;
	private Long    accountId;
	private String  shortDescription;
	private String  longDescription;
	private String  flvUrl;
	private Date    creationDate;
	private Date    publishedDate;
	private Date    lastModifiedDate;
	private Date    startDate;
	private Date    endDate;
	private String  linkUrl;
	private String  linkText;
	private String  videoStillUrl;
	private String  thumbnailUrl;
	private Long    length;
	private Boolean geoFiltered;
	private Boolean geoFilteredExclude;
	private Integer playsTotal;
	private Integer playsTrailingWeek;
	private Date    releaseDate;
	
	private List<Rendition> renditions;
	private Rendition       videoFullLength;
	
	private ItemStateEnum itemState;
	
	private List<String> tags;
	
	private EconomicsEnum economics;
	
	private List<GeoFilterCodeEnum> geoFilteredCountries;
	
	private List<CuePoint> cuePoints;
	
	private List<CustomField> customFields;
	
	/**
	 * <p>
	 *    Default Constructor.
	 * </p>
	 * 
	 * <p>
	 *    All fields set to null to start - required fields must be set before
	 *    calling Write Media API.
	 * </p>
	 * 
	 */
	public Video(){
		initAll();
	}
	
	/**
	 * <p>
	 *    Constructor using JSON string.
	 * </p>
	 * 
	 * <p>
	 *    Given a JSON string from the Media API, attempts to construct a new
	 *    Video object and fill out all of the fields defined.  All other
	 *    fields will be null.
	 * </p>
	 * 
	 */
	public Video(String json) throws JSONException {
		initAll();
		
		if(json == null){
			throw new JSONException("[ERR] Video can not be parsed from null JSON string.");
		}
		
		JSONObject jsonObj = new JSONObject(json);
		
		finishConstruction(jsonObj);
	}
	
	/**
	 * <p>
	 *    Constructor using JSON object.
	 * </p>
	 * 
	 * <p>
	 *    Given a JSON object from the Media API, attempts to construct a new
	 *    Video object and fill out all of the fields defined.  All other
	 *    fields will be null.
	 * </p>
	 * 
	 */
	public Video(JSONObject jsonObj) throws JSONException {
		finishConstruction(jsonObj);
	}
	
	/**
	 * <p>
	 *    Private method to finish construction for other constructors
	 * </p>
	 * 
	 * @param jsonObj
	 * @throws JSONException
	 */
	private void finishConstruction(JSONObject jsonObj) throws JSONException {
		String[] rootKeys = JSONObject.getNames(jsonObj);
		
		for(String rootKey : rootKeys){
			Object rootValue = jsonObj.get(rootKey);
			
			if((rootValue == null) || ("null".equals(rootValue.toString()))){
				// Don't bother setting the attribute, it should already be null
			}
			else if("name".equals(rootKey)){
				name = (String)rootValue;
			}
			else if("id".equals(rootKey)){
				if(rootValue instanceof Integer){
					// Some JVMs seem to be returning this as an integer instead of a long...
					id = new Long((Integer)rootValue);
				}
				else{
					id = (Long)rootValue;
				}
			}
			else if("creationDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				creationDate = new Date(rootLong);
			}
			else if("accountId".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				accountId = rootLong;
			}
			else if("startDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				startDate = new Date(rootLong);
			}
			else if("endDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				endDate = new Date(rootLong);
			}
			else if("FLVFullLength".equals(rootKey)){
				videoFullLength = new Rendition(((JSONObject)rootValue).toString());
			}
			else if("itemState".equals(rootKey)){
				if(rootValue.toString().equals("ACTIVE")){
					itemState = ItemStateEnum.ACTIVE;
				}
				else if(rootValue.toString().equals("DELETED")){
					itemState = ItemStateEnum.DELETED;
				}
				else if(rootValue.toString().equals("INACTIVE")){
					itemState = ItemStateEnum.INACTIVE;
				}
				else{
					throw new JSONException("[ERR] Media API specified invalid value for item state '" + rootValue + "'.  Acceptable values are 'ACTIVE', 'DELETED' and 'INACTIVE'.");
				}
			}
			else if("geoFilterExclude".equals(rootKey)){
				geoFilteredExclude = jsonObj.getBoolean(rootKey);
			}
			else if("excludeListedCountries".equals(rootKey)){
				geoFilteredExclude = jsonObj.getBoolean(rootKey);
			}
			else if("playsTotal".equals(rootKey)){
				playsTotal = jsonObj.getInt(rootKey);
			}
			else if("geoFiltered".equals(rootKey)){
				geoFiltered = jsonObj.getBoolean(rootKey);
			}
			else if("geoRestricted".equals(rootKey)){
				geoFiltered = jsonObj.getBoolean(rootKey);
			}
			else if("version".equals(rootKey)){
				// Not currently handled...
				// Usually looks like: 'version'='5'
			}
			else if("playsTrailingWeek".equals(rootKey)){
				playsTrailingWeek = jsonObj.getInt(rootKey);
			}
			else if("length".equals(rootKey)){
				length = jsonObj.getLong(rootKey);
			}
			else if("thumbnailURL".equals(rootKey)){
				thumbnailUrl = rootValue.toString();
			}
			else if("tags".equals(rootKey)){
				tags = new ArrayList<String>();
				
				JSONArray tagArray = jsonObj.getJSONArray(rootKey);
				for(int tagIdx=0;tagIdx<tagArray.length();tagIdx++){
					String tag = tagArray.get(tagIdx).toString();
					tags.add(tag);
				}
			}
			else if("videoStillURL".equals(rootKey)){
				videoStillUrl = rootValue.toString();
			}
			else if("referenceId".equals(rootKey)){
				referenceId = rootValue.toString();
			}
			else if("FLVURL".equals(rootKey)){
				flvUrl = rootValue.toString();
			}
			else if("videoFullLength".equals(rootKey)){
				videoFullLength = new Rendition(((JSONObject)rootValue).toString());
			}
			else if("shortDescription".equals(rootKey)){
				shortDescription = rootValue.toString();
			}
			else if("lastModifiedDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				lastModifiedDate = new Date(rootLong);
			}
			else if("releaseDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				lastModifiedDate = new Date(rootLong);
			}
			else if("geoFilteredCountries".equals(rootKey)){
				geoFilteredCountries = new ArrayList<GeoFilterCodeEnum>();
				
				JSONArray countryArray = jsonObj.getJSONArray(rootKey);
				for(int countryIdx=0;countryIdx<countryArray.length();countryIdx++){
					String country = countryArray.get(countryIdx).toString();
					
					for(GeoFilterCodeEnum gfce : GeoFilterCodeEnum.values()){
						if(gfce.getCode().equals(country)){
							geoFilteredCountries.add(gfce);
						}
					}
				}
			}
			else if("allowedCountries".equals(rootKey)){
				geoFilteredCountries = new ArrayList<GeoFilterCodeEnum>();
				
				JSONArray countryArray = jsonObj.getJSONArray(rootKey);
				for(int countryIdx=0;countryIdx<countryArray.length();countryIdx++){
					String country = countryArray.get(countryIdx).toString();
					
					for(GeoFilterCodeEnum gfce : GeoFilterCodeEnum.values()){
						if(gfce.getCode().equals(country)){
							geoFilteredCountries.add(gfce);
						}
					}
				}
			}
			else if("economics".equals(rootKey)){
				for(EconomicsEnum ee : EconomicsEnum.values()){
					if(ee.toString().equals(rootValue.toString())){
						economics = ee;
					}
				}
			}
			else if("publishedDate".equals(rootKey)){
				Long rootLong = jsonObj.getLong(rootKey);
				publishedDate = new Date(rootLong);
			}
			else if("longDescription".equals(rootKey)){
				longDescription = rootValue.toString();
			}
			else if("linkText".equals(rootKey)){
				this.linkText = rootValue.toString();
			}
			else if("linkURL".equals(rootKey)){
				this.linkUrl = rootValue.toString();
			}
			else if("renditions".equals(rootKey)){
				renditions = new ArrayList<Rendition>();
				
				JSONArray rendArray = jsonObj.getJSONArray(rootKey);
				for(int rendIdx=0;rendIdx<rendArray.length();rendIdx++){
					String    rend      = rendArray.get(rendIdx).toString();
					Rendition rendition = new Rendition(rend);
					renditions.add(rendition);
				}
			}
			else if("cuePoints".equals(rootKey)){
				cuePoints = new ArrayList<CuePoint>();
				
				JSONArray cueArray = jsonObj.getJSONArray(rootKey);
				for(int cueIdx=0;cueIdx<cueArray.length();cueIdx++){
					String   cue      = cueArray.get(cueIdx).toString();
					CuePoint cuePoint = new CuePoint(cue);
					cuePoints.add(cuePoint);
				}
			}
			else if("customFields".equals(rootKey)){
				customFields = new ArrayList<CustomField>();
				
				JSONObject customObj  = jsonObj.getJSONObject(rootKey);
				String[]   customKeys = JSONObject.getNames(customObj);
				if(customKeys != null) {
					for(String customKey : customKeys){
						String customValue = customObj.getString(customKey);
						
						CustomField customField = new CustomField(customKey, customValue);
						customFields.add(customField);
					}
				}
			}
			else{
				throw new JSONException("[ERR] Unknown root key '" + rootKey + "'='" + rootValue + "'.");
			}
		}
	}
	
	/**
	 * <p>
	 *    Fully initializes the video object by setting all fields to null
	 * </p>
	 */
	public void initAll() {
		name               = null;
		id                 = null;
		referenceId        = null;
		accountId          = null;
		shortDescription   = null;
		longDescription    = null;
		flvUrl             = null;
		creationDate       = null;
		publishedDate      = null;
		lastModifiedDate   = null;
		startDate          = null;
		endDate            = null;
		linkUrl            = null;
		linkText           = null;
		videoStillUrl      = null;
		thumbnailUrl       = null;
		length             = null;
		geoFiltered        = null;
		geoFilteredExclude = null;
		playsTotal         = null;
		playsTrailingWeek  = null;
		releaseDate        = null;
		
		renditions      = null;
		videoFullLength = null;
		
		itemState = null;
		
		tags = null;
		
		economics = null;
		
		geoFilteredCountries = null;
		
		cuePoints = null;
		
		customFields = null;
	}
	
	/**
	 * <p>
	 *    Gets the name (title) for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: name<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The title of the Video, limited to 255
	 *                         characters. The name is a required property
	 *                         when you create a video.
	 *    </code>
	 * </p>
	 * 
	 * @return Name of the Video
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * <p>
	 *    Sets the name (title) for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: name<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The title of the Video, limited to 255
	 *                         characters. The name is a required property
	 *                         when you create a video.
	 *    </code>
	 * </p>
	 * 
	 * @param name Name of the Video
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * <p>
	 *    Gets the id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: id<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A number that uniquely identifies this Video,
	 *                         assigned by Brightcove when the Video is
	 *                         created.
	 *    </code>
	 * </p>
	 * 
	 * @return The id for this Video
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * <p>
	 *    Sets the id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: id<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A number that uniquely identifies this Video,
	 *                         assigned by Brightcove when the Video is
	 *                         created.
	 *    </code>
	 * </p>
	 * 
	 * @param id The id for this Video
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * <p>
	 *    Gets the reference id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: referenceId<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A user-specified id that uniquely identifies
	 *                         the Video, limited to 150 characters. A
	 *                         referenceId can be used as a foreign-key to
	 *                         identify this video in another system. Note
	 *                         that that the find_videos_by_reference_ids
	 *                         method cannot handle a referenceId that contain
	 *                         commas, so you may want to avoid using commas
	 *                         in referenceId values.
	 *    </code>
	 * </p>
	 * 
	 * @return Reference id for this Video
	 */
	public String getReferenceId(){
		return referenceId;
	}
	
	/**
	 * <p>
	 *    Sets the reference id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: referenceId<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A user-specified id that uniquely identifies
	 *                         the Video, limited to 150 characters. A
	 *                         referenceId can be used as a foreign-key to
	 *                         identify this video in another system. Note
	 *                         that that the find_videos_by_reference_ids
	 *                         method cannot handle a referenceId that contain
	 *                         commas, so you may want to avoid using commas
	 *                         in referenceId values.
	 *    </code>
	 * </p>
	 * 
	 * @param referenceId Reference id for this Video
	 */
	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}
	
	/**
	 * <p>
	 *    Gets the account id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: accountId<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A number, assigned by Brightcove, that uniquely
	 *                         identifies the account to which the Video
	 *                         belongs.
	 *    </code>
	 * </p>
	 * 
	 * @return Account id for this Video 
	 */
	public Long getAccountId(){
		return accountId;
	}
	
	/**
	 * <p>
	 *    Sets the account id for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: accountId<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A number, assigned by Brightcove, that uniquely
	 *                         identifies the account to which the Video
	 *                         belongs.
	 *    </code>
	 * </p>
	 * 
	 * @param accountId Account id for this Video 
	 */
	public void setAccountId(Long accountId){
		this.accountId = accountId;
	}
	
	/**
	 * <p>
	 *    Gets the short description for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: shortDescription<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A short description describing the Video,
	 *                         limited to 250 characters. The shortDescription
	 *                         is a required property when you create a video.
	 *    </code>
	 * </p>
	 * 
	 * @return Short description for this Video
	 */
	public String getShortDescription(){
		return shortDescription;
	}
	
	/**
	 * <p>
	 *    Sets the short description for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: shortDescription<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A short description describing the Video,
	 *                         limited to 250 characters. The shortDescription
	 *                         is a required property when you create a video.
	 *    </code>
	 * </p>
	 * 
	 * @param shortDescription Short description for this Video
	 */
	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}
	
	/**
	 * <p>
	 *    Gets the long description for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: longDescription<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A longer description of this Video, limited to
	 *                         5000 characters.
	 *    </code>
	 * </p>
	 * 
	 * @return Long description for this Video
	 */
	public String getLongDescription(){
		return longDescription;
	}
	
	/**
	 * <p>
	 *    Sets the long description for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: longDescription<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A longer description of this Video, limited to
	 *                         5000 characters.
	 *    </code>
	 * </p>
	 * 
	 * @param longDescription Long description for this Video
	 */
	public void setLongDescription(String longDescription){
		this.longDescription = longDescription;
	}
	
	/**
	 * <p>
	 *    Gets the FLV URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: FLVURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL of the video file for this Video. Note
	 *                         that this property can be accessed with the
	 *                         Media API only with a special read or write
	 *                         token. This property applies, no matter whether
	 *                         the video's encoding is FLV (VP6) or MP4
	 *                         (H.264). See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @return FLV URL for this Video 
	 */
	public String getFlvUrl(){
		return flvUrl;
	}
	
	/**
	 * <p>
	 *    Sets the FLV URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: FLVURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL of the video file for this Video. Note
	 *                         that this property can be accessed with the
	 *                         Media API only with a special read or write
	 *                         token. This property applies, no matter whether
	 *                         the video's encoding is FLV (VP6) or MP4
	 *                         (H.264). See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @param flvUrl FLV URL for this Video 
	 */
	public void setFlvUrl(String flvUrl){
		this.flvUrl = flvUrl;
	}
	
	/**
	 * <p>
	 *    Gets the renditions for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: renditions<br/>
	 *          Type:          Array<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An array of
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">Renditions</a>
	 *                         that represent the multi-bitrate streaming
	 *                         renditions available for this Video. A Video
	 *                         should have not more than 10 Renditions. Note
	 *                         that this property can be accessed with the
	 *                         Media API only with a special read or write
	 *                         token. See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @return List of renditions for this Video 
	 */
	public List<Rendition> getRenditions(){
		return renditions;
	}
	
	/**
	 * <p>
	 *    Sets the renditions for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: renditions<br/>
	 *          Type:          Array<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An array of
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">Renditions</a>
	 *                         that represent the multi-bitrate streaming
	 *                         renditions available for this Video. A Video
	 *                         should have not more than 10 Renditions. Note
	 *                         that this property can be accessed with the
	 *                         Media API only with a special read or write
	 *                         token. See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @param renditions List of renditions for this Video 
	 */
	public void setRenditions(List<Rendition> renditions){
		this.renditions = renditions;
	}
	
	/**
	 * <p>
	 *    Gets the full length rendition for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoFullLength<br/>
	 *          Type:          Rendition<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A single
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">Rendition</a>
	 *                         that represents the the video file for the
	 *                         Video. Note that this property can be accessed
	 *                         with the Media API only with a special read or
	 *                         write token. See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @return The full length rendition for this Video
	 */
	public Rendition getVideoFullLength(){
		return videoFullLength;
	}
	
	/**
	 * <p>
	 *    Sets the full length rendition for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoFullLength<br/>
	 *          Type:          Rendition<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A single
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#Rendition">Rendition</a>
	 *                         that represents the the video file for the
	 *                         Video. Note that this property can be accessed
	 *                         with the Media API only with a special read or
	 *                         write token. See
	 *                         <a href="http://support.brightcove.com/en/docs/accessing-video-content-media-api">Accessing Video Content with the Media API</a>.
	 *    </code>
	 * </p>
	 * 
	 * @param videoFullLength The full length rendition for this Video
	 */
	public void setVideoFullLength(Rendition videoFullLength){
		this.videoFullLength = videoFullLength;
	}
	
	/**
	 * <p>
	 *    Gets the creation date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: creationDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was created, represented as
	 *                         the number of milliseconds since the UNIX
	 *                         epoch.
	 *    </code>
	 * </p>
	 * 
	 * @return Creation date for this Video
	 */
	public Date getCreationDate(){
		return creationDate;
	}
	
	/**
	 * <p>
	 *    Sets the creation date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: creationDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was created, represented as
	 *                         the number of milliseconds since the UNIX
	 *                         epoch.
	 *    </code>
	 * </p>
	 * 
	 * @param creationDate Creation date for this Video
	 */
	public void setCreationDate(Date creationDate){
		this.creationDate = creationDate;
	}
	
	/**
	 * <p>
	 *    Gets the published date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: publishedDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was last made active,
	 *                         represented as the number of milliseconds since
	 *                         the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @return Published date for this Video 
	 */
	public Date getPublishedDate(){
		return publishedDate;
	}
	
	/**
	 * <p>
	 *    Sets the published date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: publishedDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was last made active,
	 *                         represented as the number of milliseconds since
	 *                         the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @param publishedDate Published date for this Video 
	 */
	public void setPublishedDate(Date publishedDate){
		this.publishedDate = publishedDate;
	}
	
	/**
	 * <p>
	 *    Gets the last modified date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: lastModifiedDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was last modified,
	 *                         represented as the number of milliseconds since
	 *                         the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @return Last modified date for this Video
	 */
	public Date getLastModifiedDate(){
		return lastModifiedDate;
	}
	
	/**
	 * <p>
	 *    Sets the last modified date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: lastModifiedDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The date this Video was last modified,
	 *                         represented as the number of milliseconds since
	 *                         the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @param lastModifiedDate Last modified date for this Video
	 */
	public void setLastModifiedDate(Date lastModifiedDate){
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * <p>Gets the release date for this Video.</p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @return Release date for this Video
	 */
	public Date getReleaseDate(){
		return releaseDate;
	}
	
	/**
	 * <p>
	 *    Sets the release date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          undocumented
	 *    </code>
	 * </p>
	 * 
	 * @param releaseDate Release date for this Video
	 */
	public void setReleaseDate(Date releaseDate){
		this.releaseDate = releaseDate;
	}
	
	/**
	 * <p>
	 *    Gets the item state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: itemState<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An
	 *                         <a href="http://docs.brightcove.com/en/media/index.html#ItemStateEnum">ItemStateEnum</a>.
	 *                         One of the properties: ACTIVE, INACTIVE, or
	 *                         DELETED. You can set this property only to
	 *                         ACTIVE or INACTIVE; you cannot delete a video
	 *                         by setting its itemState to DELETED.
	 *    </code>
	 * </p>
	 * 
	 * @return The item state for this Video
	 */
	public ItemStateEnum getItemState(){
		return itemState;
	}
	
	/**
	 * <p>
	 *    Sets the item state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: itemState<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An
	 *                         <a href="http://docs.brightcove.com/en/media/index.html#ItemStateEnum">ItemStateEnum</a>.
	 *                         One of the properties: ACTIVE, INACTIVE, or
	 *                         DELETED. You can set this property only to
	 *                         ACTIVE or INACTIVE; you cannot delete a video
	 *                         by setting its itemState to DELETED.
	 *    </code>
	 * </p>
	 * 
	 * @param itemState The item state for this Video
	 */
	public void setItemState(ItemStateEnum itemState){
		this.itemState = itemState;
	}
	
	/**
	 * <p>
	 *    Gets the start date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: startDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The first date this Video is available to be
	 *                         played, represented as the number of
	 *                         milliseconds since the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @return Start date for this Video
	 */
	public Date getStartDate(){
		return startDate;
	}
	
	/**
	 * <p>
	 *    Sets the start date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: startDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The first date this Video is available to be
	 *                         played, represented as the number of
	 *                         milliseconds since the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @param startDate Start date for this Video
	 */
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	
	/**
	 * <p>
	 *    Gets the end date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: endDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The last date this Video is available to be
	 *                         played, represented as the number of
	 *                         milliseconds since the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @return End date for this Video
	 */
	public Date getEndDate(){
		return endDate;
	}
	
	/**
	 * <p>
	 *    Sets the end date for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: endDate<br/>
	 *          Type:          Date<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The last date this Video is available to be
	 *                         played, represented as the number of
	 *                         milliseconds since the UNIX epoch.
	 *    </code>
	 * </p>
	 * 
	 * @param endDate End date for this Video
	 */
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	
	/**
	 * <p>
	 *    Gets the related link URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: linkURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An optional URL to a related item, limited to
	 *                         255 characters.
	 *    </code>
	 * </p>
	 * 
	 * @return Link URL for this Video
	 */
	public String getLinkUrl(){
		return linkUrl;
	}
	
	/**
	 * <p>
	 *    Sets the related link URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: linkURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An optional URL to a related item, limited to
	 *                         255 characters.
	 *    </code>
	 * </p>
	 * 
	 * @param linkUrl Link URL for this Video
	 */
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	
	/**
	 * <p>
	 *    Gets the related link text for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: linkText<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The text displayed for the linkURL, limited to
	 *                         255 characters.
	 *    </code>
	 * </p>
	 * 
	 * @return Link text for this Video
	 */
	public String getLinkText(){
		return linkText;
	}
	
	/**
	 * <p>
	 *    Sets the related link text for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: linkText<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   The text displayed for the linkURL, limited to
	 *                         255 characters.
	 *    </code>
	 * </p>
	 * 
	 * @param linkText Link text for this Video
	 */
	public void setLinkText(String linkText){
		this.linkText = linkText;
	}
	
	/**
	 * <p>
	 *    Gets the keyword tags for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: tags<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of Strings representing the tags
	 *                         assigned to this Video. Each tag can be not
	 *                         more than 128 characters, and a video can have
	 *                         no more than 1200 tags.
	 *    </code>
	 * </p>
	 * 
	 * @return The keyword tags for this Video 
	 */
	public List<String> getTags(){
		return tags;
	}
	
	/**
	 * <p>
	 *    Sets the keyword tags for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: tags<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of Strings representing the tags
	 *                         assigned to this Video. Each tag can be not
	 *                         more than 128 characters, and a video can have
	 *                         no more than 1200 tags.
	 *    </code>
	 * </p>
	 * 
	 * @param tags The keyword tags for this Video 
	 */
	public void setTags(List<String> tags){
		this.tags = tags;
	}
	
	/**
	 * <p>
	 *    Gets the video still URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoStillURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL to the video still image associated
	 *                         with this Video. Video stills are 480x360
	 *                         pixels.
	 *    </code>
	 * </p>
	 * 
	 * @return Video still URL for this Video
	 */
	public String getVideoStillUrl(){
		return videoStillUrl;
	}
	
	/**
	 * <p>
	 *    Sets the video still URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoStillURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL to the video still image associated
	 *                         with this Video. Video stills are 480x360
	 *                         pixels.
	 *    </code>
	 * </p>
	 * 
	 * @param videoStillUrl Video still URL for this Video
	 */
	public void setVideoStillUrl(String videoStillUrl){
		this.videoStillUrl = videoStillUrl;
	}
	
	/**
	 * <p>
	 *    Gets the thumbnail URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: thumbnailURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL to the thumbnail image associated with
	 *                         this Video. Thumbnails are 120x90 pixels.
	 *    </code>
	 * </p>
	 * 
	 * @return Thumbnail URL for this Video
	 */
	public String getThumbnailUrl(){
		return thumbnailUrl;
	}
	
	/**
	 * <p>
	 *    Sets the thumbnail URL for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: thumbnailURL<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The URL to the thumbnail image associated with
	 *                         this Video. Thumbnails are 120x90 pixels.
	 *    </code>
	 * </p>
	 * 
	 * @param thumbnailUrl Thumbnail URL for this Video
	 */
	public void setThumbnailUrl(String thumbnailUrl){
		this.thumbnailUrl = thumbnailUrl;
	}
	
	/**
	 * <p>
	 *    Gets the length for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: length<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The length of this video in milliseconds.
	 *    </code>
	 * </p>
	 * 
	 * @return Length for this Video
	 */
	public Long getLength(){
		return length;
	}
	
	/**
	 * <p>
	 *    Sets the length for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: length<br/>
	 *          Type:          long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   The length of this video in milliseconds.
	 *    </code>
	 * </p>
	 * 
	 * @param length Length for this Video
	 */
	public void setLength(Long length){
		this.length = length;
	}
	
	/**
	 * <p>
	 *    Gets the custom fields for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: customFields<br/>
	 *          Type:          Object<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A map of names and values for
	 *                         <a href="http://support.brightcove.com/en/docs/setting-custom-metadata">custom fields set up for videos in your account</a>.
	 *                         <a href="http://support.brightcove.com/en/docs/setting-custom-metadata-media-api">More information and examples</a>.
	 *    </code>
	 * </p>
	 * 
	 * @return The list of custom fields to use for this video
	 */
	public List<CustomField> getCustomFields(){
		return customFields;
	}
	
	/**
	 * <p>
	 *    Sets the custom fields for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: customFields<br/>
	 *          Type:          Object<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A map of names and values for
	 *                         <a href="http://support.brightcove.com/en/docs/setting-custom-metadata">custom fields set up for videos in your account</a>.
	 *                         <a href="http://support.brightcove.com/en/docs/setting-custom-metadata-media-api">More information and examples</a>.
	 *    </code>
	 * </p>
	 * 
	 * @param customFields The list of custom fields to use for this video
	 */
	public void setCustomFields(List<CustomField> customFields){
		this.customFields = customFields;
	}
	
	/**
	 * <p>
	 *    Gets the economics for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: economics<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An
	 *                         <a href="http://docs.brightcove.com/en/media/#EconomicsEnum">EconomicsEnum</a>.
	 *                         Either FREE or AD_SUPPORTED. AD_SUPPORTED means
	 *                         that ad requests are enabled for the Video.
	 *    </code>
	 * </p>
	 * 
	 * @return The economics for this Video
	 */
	public EconomicsEnum getEconomics(){
		return economics;
	}
	
	/**
	 * <p>
	 *    Sets the economics for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: economics<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    no<br/>
	 *          Description:   An
	 *                         <a href="http://docs.brightcove.com/en/media/#EconomicsEnum">EconomicsEnum</a>.
	 *                         Either FREE or AD_SUPPORTED. AD_SUPPORTED means
	 *                         that ad requests are enabled for the Video.
	 *    </code>
	 * </p>
	 * 
	 * @param economics The economics for this Video
	 */
	public void setEconomics(EconomicsEnum economics){
		this.economics = economics;
	}
	
	
	
	
	// ******* ToDo - AdKeys
	// * <p>
	// *    <code>
	// *          Property name: adKeys<br/>
	// *          Type:          String<br/>
	// *          Read only?:    no<br/>
	// *          Description:   A string representing the ad
	// *                         <a href="http://support.brightcove.com/en/docs/setting-ad-policies-videos#keyvalue">key/value pairs</a>
	// *                         assigned to the video. Key/value pairs are
	// *                         formatted as key=value and are separated by
	// *                         ampersands (&). For example:<br/>
	// *                         "adKeys":"category=sports&live=true"
	// *    </code>
	// * </p>
	
	
	
	
	
	/**
	 * <p>
	 *    Gets the geo filtered state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoRestricted<br/>
	 *          Type:          boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   True indicates that the video is
	 *                         <a href="http://support.brightcove.com/en/docs/geo-filtering-media-api">geo-restricted</a>.
	 *    </code>
	 * </p>
	 * 
	 * @return Geo filtered state for this Video 
	 */
	public Boolean getGeoFiltered(){
		return geoFiltered;
	}
	
	/**
	 * <p>
	 *    Sets the geo filtered state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoRestricted<br/>
	 *          Type:          boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   True indicates that the video is
	 *                         <a href="http://support.brightcove.com/en/docs/geo-filtering-media-api">geo-restricted</a>.
	 *    </code>
	 * </p>
	 * 
	 * @param geoFiltered Geo filtered state for this Video 
	 */
	public void setGeoFiltered(Boolean geoFiltered){
		this.geoFiltered = geoFiltered;
	}
	
	/**
	 * <p>
	 *    Gets the geo-filtered countries for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoFilteredCountries<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of the
	 *                         <a href="http://www.iso.org/iso/english_country_names_and_code_elements">ISO-3166 two-letter codes</a>
	 *                         of the countries to enforce geo-restriction
	 *                         rules on. Use lowercase for the country codes.
	 *    </code>
	 * </p>
	 * 
	 * @return The geo-filtered countries for this Video
	 */
	public List<GeoFilterCodeEnum> getGeoFilteredCountries(){
		return geoFilteredCountries;
	}
	
	/**
	 * <p>
	 *    Sets the geo-filtered countries for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoFilteredCountries<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of the
	 *                         <a href="http://www.iso.org/iso/english_country_names_and_code_elements">ISO-3166 two-letter codes</a>
	 *                         of the countries to enforce geo-restriction
	 *                         rules on. Use lowercase for the country codes.
	 *    </code>
	 * </p>
	 * 
	 * @param geoFilteredCountries The geo-filtered countries for this Video
	 */
	public void setGeoFilteredCountries(List<GeoFilterCodeEnum> geoFilteredCountries){
		this.geoFilteredCountries = geoFilteredCountries;
	}
	
	/**
	 * <p>
	 *    Gets the geo filtered exclude state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoFilterExclude<br/>
	 *          Type:          boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   If true, the video can be viewed in all
	 *                         countries except those listed in
	 *                         geoFiltereCountries; if false, the video can be
	 *                         viewed only in the countries listed in
	 *                         geoFilteredCountries.
	 *    </code>
	 * </p>
	 * 
	 * @return Geo filtered exclude state for this Video
	 */
	public Boolean getGeoFilteredExclude(){
		return geoFilteredExclude;
	}
	
	/**
	 * <p>
	 *    Sets the geo filtered exclude state for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: geoFilterExclude<br/>
	 *          Type:          boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   If true, the video can be viewed in all
	 *                         countries except those listed in
	 *                         geoFiltereCountries; if false, the video can be
	 *                         viewed only in the countries listed in
	 *                         geoFilteredCountries.
	 *    </code>
	 * </p>
	 * 
	 * @param geoFilteredExclude Geo filtered exclude state for this Video
	 */
	public void setGeoFilteredExclude(Boolean geoFilteredExclude){
		this.geoFilteredExclude = geoFilteredExclude;
	}
	
	/**
	 * <p>
	 *    Gets the cue points for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: cuePoints<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of the
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint">CuePoints</a>
	 *                         objects assigned to this Video.
	 *    </code>
	 * </p>
	 * 
	 * @return The cue points for this Video
	 */
	public List<CuePoint> getCuePoints(){
		return cuePoints;
	}
	
	/**
	 * <p>
	 *    Sets the cue points for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: cuePoints<br/>
	 *          Type:          List<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A list of the
	 *                         <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint">CuePoints</a>
	 *                         objects assigned to this Video.
	 *    </code>
	 * </p>
	 * 
	 * @param cuePoints The cue points for this Video
	 */
	public void setCuePoints(List<CuePoint> cuePoints){
		this.cuePoints = cuePoints;
	}
	
	/**
	 * <p>
	 *    Gets the plays total count for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: playsTotal<br/>
	 *          Type:          Integer<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   How many times the Video has been played since
	 *                         its creation.
	 *    </code>
	 * </p>
	 * 
	 * @return Plays total count for this Video
	 */
	public Integer getPlaysTotal(){
		return playsTotal;
	}
	
	/**
	 * <p>
	 *    Sets the plays total count for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: playsTotal<br/>
	 *          Type:          Integer<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   How many times the Video has been played since
	 *                         its creation.
	 *    </code>
	 * </p>
	 * 
	 * @param playsTotal Plays total count for this Video
	 */
	public void setPlaysTotal(Integer playsTotal){
		this.playsTotal = playsTotal;
	}
	
	/**
	 * <p>
	 *    Gets the plays trailing week count for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: playsTrailingWeek<br/>
	 *          Type:          Integer<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   How many times the Video has been played within
	 *                         the past seven days, exclusive of today.
	 *    </code>
	 * </p>
	 * 
	 * @return Plays trailing week count for this Video
	 */
	public Integer getPlaysTrailingWeek(){
		return playsTrailingWeek;
	}
	
	/**
	 * <p>
	 *    Sets the plays trailing week count for this Video.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: playsTrailingWeek<br/>
	 *          Type:          Integer<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   How many times the Video has been played within
	 *                         the past seven days, exclusive of today.
	 *    </code>
	 * </p>
	 * 
	 * @param playsTrailingWeek Plays trailing week count for this Video
	 */
	public void setPlaysTrailingWeek(Integer playsTrailingWeek){
		this.playsTrailingWeek = playsTrailingWeek;
	}
	
	/**
	 * <p>Converts the video into a JSON object suitable for use with the Media API</p>
	 * 
	 * @return JSON object representing the video
	 */
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		
		if(name != null){
			json.put("name", name);
		}
		if(id != null){
			json.put("id",   id);
		}
		if(referenceId != null){
			json.put("referenceId", referenceId);
		}
		if(accountId != null){
			json.put("accountId", accountId);
		}
		if(shortDescription != null){
			json.put("shortDescription", shortDescription);
		}
		if(longDescription != null){
			json.put("longDescription", longDescription);
		}
		if(flvUrl != null){
			json.put("FLVURL", flvUrl);
		}
		if(creationDate != null){
			json.put("creationDate", creationDate.getTime());
		}
		if(publishedDate != null){
			json.put("publishedDate", publishedDate.getTime());
		}
		if(lastModifiedDate != null){
			json.put("lastModifiedDate", lastModifiedDate.getTime());
		}
		if(releaseDate != null){
			json.put("releaseDate", releaseDate.getTime());
		}
		if(startDate != null){
			json.put("startDate", startDate.getTime());
		}
		if(endDate != null){
			json.put("endDate", endDate.getTime());
		}
		if(linkUrl != null){
			json.put("linkURL", linkUrl);
		}
		if(linkText != null){
			json.put("linkText", linkText);
		}
		if(videoStillUrl != null){
			json.put("videoStillURL", videoStillUrl);
		}
		if(thumbnailUrl != null){
			json.put("thumbnailURL", thumbnailUrl);
		}
		if(length != null){
			json.put("length", length);
		}
		if(geoFiltered != null){
			// json.put("geoFiltered", geoFiltered);
			
			// Change to Media API (circa 3.2.x maybe?) "geoFiltered" is
			// now "geoRestricted"
			json.put("geoRestricted", geoFiltered);
		}
		if(geoFilteredExclude != null){
			json.put("geoFilterExclude", geoFilteredExclude);
		}
		if(playsTotal != null){
			json.put("playsTotal", playsTotal);
		}
		if(playsTrailingWeek != null){
			json.put("playsTrailingWeek", playsTrailingWeek);
		}
		if(renditions != null){
			JSONArray jsonRenditions = new JSONArray();
			for(Rendition rendition : renditions){
				JSONObject renditionJson = rendition.toJson();
				jsonRenditions.put(renditionJson);
			}
			
			json.put("renditions", jsonRenditions);
		}
		if(videoFullLength != null){
			json.put("videoFullLength", videoFullLength.toJson());
		}
		if(itemState != null){
			json.put("itemState", itemState);
		}
		if(tags != null){
			JSONArray tagArray = new JSONArray();
			for(String tag : tags){
				tagArray.put(tag);
			}
			json.put("tags", tagArray);
		}
		if(economics != null){
			json.put("economics", economics);
		}
		if(geoFilteredCountries != null){
			JSONArray countryArray = new JSONArray();
			for(GeoFilterCodeEnum country : geoFilteredCountries){
				countryArray.put(country.getCode());
			}
			json.put("geoFilteredCountries", countryArray);
		}
		if(cuePoints != null){
			JSONArray cuePointsJson = new JSONArray();
			for(CuePoint cuePoint : cuePoints){
				JSONObject cuePointJson = cuePoint.toJson();
				cuePointsJson.put(cuePointJson);
			}
			json.put("cuePoints", cuePointsJson);
		}
		if(customFields != null){
			JSONObject customFieldsJson = new JSONObject();
			for(CustomField customField : customFields){
				if(customField.getName() != null ) {
					customFieldsJson.put(customField.getName(), customField.getValue());
				}
			}
			json.put("customFields", customFieldsJson);
		}
		
		return json;
	}
	
	/**
	 * <p>
	 *    Uses the json.org libraries to generate an XML representation of
	 *    this video object.
	 * </p>
	 * 
	 * @return String with XML representing the video object 
	 * @throws JSONException Because under the covers we're using the json.org libraries to generate the XML
	 */
	public String toXmlString() throws JSONException {
		JSONObject jsono = toJson();
		if(jsono == null){ return null; }
		
		String ret = org.json.XML.toString(jsono, "Video");
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String ret = "[com.brightcove.proserve.mediaapi.wrapper.apiobjects.Video (\n" +
			"\tname:'"                 + name                 + "'\n" +
			"\tid:'"                   + id                   + "'\n" +
			"\treferenceId:'"          + referenceId          + "'\n" +
			"\taccountId:'"            + accountId            + "'\n" +
			"\tshortDescription:'"     + shortDescription     + "'\n" +
			"\tlongDescription:'"      + longDescription      + "'\n" +
			"\tflvUrl:'"               + flvUrl               + "'\n" +
			"\tcreationDate:'"         + creationDate         + "'\n" +
			"\tpublishedDate:'"        + publishedDate        + "'\n" +
			"\tlastModifiedDate:'"     + lastModifiedDate     + "'\n" +
			"\treleaseDate:'"          + releaseDate          + "'\n" +
			"\tstartDate:'"            + startDate            + "'\n" +
			"\tendDate:'"              + endDate              + "'\n" +
			"\tlinkUrl:'"              + linkUrl              + "'\n" +
			"\tlinkText:'"             + linkText             + "'\n" +
			"\tvideoStillUrl:'"        + videoStillUrl        + "'\n" +
			"\tthumbnailUrl:'"         + thumbnailUrl         + "'\n" +
			"\tlength:'"               + length               + "'\n" +
			"\tgeoFiltered:'"          + geoFiltered          + "'\n" +
			"\tgeoFilteredExclude:'"   + geoFilteredExclude   + "'\n" +
			"\tplaysTotal:'"           + playsTotal           + "'\n" +
			"\tplaysTrailingWeek:'"    + playsTrailingWeek    + "'\n" +
			"\trenditions:'"           + renditions           + "'\n" +
			"\tvideoFullLength:'"      + videoFullLength      + "'\n" +
			"\titemState:'"            + itemState            + "'\n" +
			"\ttags:'"                 + tags                 + "'\n" +
			"\teconomics:'"            + economics            + "'\n" +
			"\tgeoFilteredCountries:'" + geoFilteredCountries + "'\n" +
			"\tcuePoints:'"            + cuePoints            + "'\n" +
			"\tcustomFields:'"         + customFields         + "'\n" +
			")]";
		
		return ret;
	}
}
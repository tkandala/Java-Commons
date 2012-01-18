package com.brightcove.commons.catalog.objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.brightcove.commons.catalog.objects.enumerations.CuePointTypeEnum;
import com.brightcove.commons.xml.W3CXMLUtils;

/**
 * <p>
 *    A Cue Point object.
 * </p>
 * 
 * <p>
 *    This object is defined primarily by the Media API documentation for
 *    a CuePoint object:
 *    <a href="http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint">http://support.brightcove.com/en/docs/media-api-objects-reference#CuePoint</a>.
 * </p>
 * <p>
 *    This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * <p>
 *     <code>
 *           The CuePoint object is a marker set at a precise time point in
 *           the duration of a video. You can use cue points to trigger
 *           mid-roll ads or to separate chapters or scenes in a long-form
 *           video. For more information, see
 *           <a href="http://support.brightcove.com/en/docs/adding-cue-points-videos">Adding Cue Points to Videos</a>
 *           and
 *           <a href="http://support.brightcove.com/en/docs/setting-cue-points-media-api">Setting CuePoints with the Media API</a>.
 *     </code>
 * </p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public class CuePoint {
	private Long             id;
	private String           name;
	private String           videoId;
	private Long             time;
	private Boolean          forceStop;
	private CuePointTypeEnum type;
	private String           metadata;
	
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
	public CuePoint(){
		initAll();
	}
	
	/**
	 * <p>Constructor using JSON string.</p>
	 * 
	 * <p>
	 *    Given a JSON string from the Media API, attempts to construct a new
	 *    Cue Point object and fill out all of the fields defined.  All other
	 *    fields will be null.
	 * </p>
	 * 
	 */
	public CuePoint(String json) throws JSONException {
		initAll();
		
		if(json == null){
			throw new JSONException("[ERR] Cue Point can not be parsed from null JSON string.");
		}
		
		JSONObject jsonObj = new JSONObject(json);
		
		String[] rootKeys = JSONObject.getNames(jsonObj);
		
		for(String rootKey : rootKeys){
			Object rootValue = jsonObj.get(rootKey);
			
			if((rootValue == null) || ("null".equals(rootValue.toString()))){
				// Don't bother setting the attribute, it should already be null
			}
			else if("forceStop".equals(rootKey)){
				forceStop = jsonObj.getBoolean(rootKey);
			}
			else if("id".equals(rootKey)){
				id = jsonObj.getLong(rootKey);
			}
			else if("metadata".equals(rootKey)){
				metadata = rootValue.toString();
			}
			else if("name".equals(rootKey)){
				name = rootValue.toString();
			}
			else if("time".equals(rootKey)){
				time = jsonObj.getLong(rootKey);
			}
			else if("type".equals(rootKey)){
				// Not currently handled - see typeEnum
			}
			else if("typeEnum".equals(rootKey)){
				for(CuePointTypeEnum typeEnum : CuePointTypeEnum.values()){
					if(typeEnum.getName().equals(rootValue.toString())){
						type = typeEnum;
					}
				}
			}
			else if("videoId".equals(rootKey)){
				videoId = rootValue.toString();
			}
			else{
				throw new JSONException("[ERR] Unknown root key '" + rootKey + "'='" + rootValue + "'.");
			}
		}
	}
	
	/**
	 * <p>
	 *    Constructs a new CuePoint from an XML representation (generated by Videos.toXml()).
	 * </p>
	 * 
	 * @param element XML Element representing the cuepoint object 
	 */
	public CuePoint(Element element) {
		initAll();
		
		Element child = W3CXMLUtils.getFirstElementChild(element);
		while(child != null){
			String nodeName  = child.getNodeName();
			String nodeValue = W3CXMLUtils.getStringValue(child);
			
			if(Node.ELEMENT_NODE == child.getNodeType()){
				if(nodeName.equals("id")){
					if(nodeValue != null){
						id = Long.parseLong(nodeValue);
					}
				}
				else if(nodeName.equals("name")){
					if(nodeValue != null){
						name = nodeValue;
					}
				}
				else if(nodeName.equals("videoId")){
					if(nodeValue != null){
						videoId = nodeValue;
					}
				}
				else if(nodeName.equals("time")){
					if(nodeValue != null){
						time = Long.parseLong(nodeValue);
					}
				}
				else if(nodeName.equals("forceStop")){
					if(nodeValue != null){
						forceStop = Boolean.parseBoolean(nodeValue);
					}
				}
				else if(nodeName.equals("type")){
					if(nodeValue != null){
						if(CuePointTypeEnum.AD.toString().equals(nodeValue)){
							type = CuePointTypeEnum.AD;
						}
						else if(CuePointTypeEnum.CHAPTER.toString().equals(nodeValue)){
							type = CuePointTypeEnum.CHAPTER;
						}
						else if(CuePointTypeEnum.CODE.toString().equals(nodeValue)){
							type = CuePointTypeEnum.CODE;
						}
					}
				}
				else if(nodeName.equals("metadata")){
					if(nodeValue != null){
						metadata = nodeValue;
					}
				}
			}
			child = W3CXMLUtils.getNextElementSibling(child);
		}
	}
	
	/**
	 * <p>
	 *    Initializes all variables to null
	 * </p>
	 */
	public void initAll(){
		id        = null;
		name      = null;
		videoId   = null;
		time      = null;
		forceStop = null;
		type      = null;
		metadata  = null;
	}
	
	/**
	 * <p>
	 *    Gets the name of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: name<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. A name for the cue point, so that you
	 *                         can refer to it.
	 *    </code>
	 * </p>
	 * 
	 * @return Name of the Cue Point
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * <p>
	 *    Sets the name of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: name<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. A name for the cue point, so that you
	 *                         can refer to it.
	 *    </code>
	 * </p>
	 * 
	 * @param name Name of the Cue Point
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * <p>
	 *    Gets the id of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          (undocumented)
	 *    </code>
	 * </p>
	 * 
	 * @return id of the Cue Point
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * <p>
	 *    Sets the id of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          (undocumented)
	 *    </code>
	 * </p>
	 * 
	 * @param id Id of the Cue Point
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * <p>
	 *    Gets the id(s) of the video(s) this cue point applies to.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoId<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A comma-separated list of the ids of one or
	 *                         more videos that this cue point applies to.
	 *    </code>
	 * </p>
	 * 
	 * @return Comma-separated String of the video ids this Cue Point applies to
	 */
	public String getVideoId(){
		return videoId;
	}
	
	/**
	 * <p>
	 *    Sets the id(s) of the video(s) this cue point applies to.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: videoId<br/>
	 *          Type:          String<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   A comma-separated list of the ids of one or
	 *                         more videos that this cue point applies to.
	 *    </code>
	 * </p>
	 * 
	 * @param videoId Comma-separated String of the video ids this Cue Point applies to
	 */
	public void setVideoId(String videoId){
		this.videoId = videoId;
	}
	
	/**
	 * <p>
	 *    Gets the time of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: time<br/>
	 *          Type:          Long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. The time of the cue point, measured
	 *                         in milliseconds from the beginning of the
	 *                         video.
	 *    </code>
	 * </p>
	 * 
	 * @return Time of the Cue Point
	 */
	public Long getTime(){
		return time;
	}
	/**
	 * <p>
	 *    Sets the time of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: time<br/>
	 *          Type:          Long<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. The time of the cue point, measured
	 *                         in milliseconds from the beginning of the
	 *                         video.
	 *    </code>
	 * </p>
	 * 
	 * @param time Time of the Cue Point
	 */
	public void setTime(Long time){
		this.time = time;
	}
	
	/**
	 * <p>
	 *    Gets the forceStop attribute of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: forceStop<br/>
	 *          Type:          Boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   If true, the video stops playback at the cue
	 *                         point. This setting is valid only for AD type
	 *                         cue points.
	 *    </code>
	 * </p>
	 * 
	 * @return forceStop attribute of the Cue Point
	 */
	public Boolean getForceStop(){
		return forceStop;
	}
	
	/**
	 * <p>
	 *    Sets the forceStop attribute of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: forceStop<br/>
	 *          Type:          Boolean<br/>
	 *          Read only?:    no<br/>
	 *          Description:   If true, the video stops playback at the cue
	 *                         point. This setting is valid only for AD type
	 *                         cue points.
	 *    </code>
	 * </p>
	 * 
	 * @param forceStop forceStop attribute of the Cue Point
	 */
	public void setForceStop(Boolean forceStop){
		this.forceStop = forceStop;
	}
	
	/**
	 * <p>
	 *    Gets the type of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: type<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. An integer code corresponding to the
	 *                         type of cue point. One of 0 (AD) or 1 (CODE).
	 *                         An Ad cue point is used to trigger mid-roll ad
	 *                         requests. A Code cue point can be used to
	 *                         indicate a chapter or scene break in the video.
	 *    </code>
	 * </p>
	 * 
	 * @return Type of the Cue Point
	 */
	public CuePointTypeEnum getType(){
		return type;
	}
	
	/**
	 * <p>
	 *    Sets the type of the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: type<br/>
	 *          Type:          Enum<br/>
	 *          Read only?:    yes<br/>
	 *          Description:   Required. An integer code corresponding to the
	 *                         type of cue point. One of 0 (AD) or 1 (CODE).
	 *                         An Ad cue point is used to trigger mid-roll ad
	 *                         requests. A Code cue point can be used to
	 *                         indicate a chapter or scene break in the video.
	 *    </code>
	 * </p>
	 * 
	 * @param type Type of the Cue Point
	 */
	public void setType(CuePointTypeEnum type){
		this.type = type;
	}
	
	/**
	 * <p>
	 *    Gets the meta data on the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: metadata<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A string that can be passed along with a CODE
	 *                         cue point. Not more than 512 characters.
	 *    </code>
	 * </p>
	 * 
	 * @return Meta data on the Cue Point
	 */
	public String getMetadata(){
		return metadata;
	}
	
	/**
	 * <p>
	 *    Sets the meta data on the Cue Point.
	 * </p>
	 * 
	 * <p>
	 *    <code>
	 *          Property name: metadata<br/>
	 *          Type:          String<br/>
	 *          Read only?:    no<br/>
	 *          Description:   A string that can be passed along with a CODE
	 *                         cue point. Not more than 512 characters.
	 *    </code>
	 * </p>
	 * 
	 * @param metadata Meta data on the Cue Point
	 */
	public void setMetadata(String metadata){
		this.metadata = metadata;
	}
	
	/**
	 * <p>
	 *    Converts the cue point into a JSON object suitable for use with the Media API
	 * </p>
	 * 
	 * @return JSON object representing the cue point
	 */
	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		
		if(id != null){
			json.put("id", id);
		}
		if(name != null){
			json.put("name", name);
		}
		if(videoId != null){
			json.put("videoId", videoId);
		}
		if(time != null){
			json.put("time", time);
		}
		if(forceStop != null){
			json.put("forceStop", forceStop);
		}
		if(type != null){
			json.put("type", type);
		}
		if(metadata != null){
			json.put("metadata", metadata);
		}
		
		return json;
	}
	
	/**
	 * <p>
	 *    Uses the W3C libraries to generate an XML representation of
	 *    this object.
	 * </p>
	 * 
	 * @param root Element to append data to
	 * @return XML Element representing the object 
	 */
	public Element appendXml(Element root) {
		Document doc = root.getOwnerDocument();
		
		Element cuePointElement = doc.createElement("cuePoint");
		root.appendChild(cuePointElement);
		
		if(id != null){
			Element idElement = doc.createElement("id");
			idElement.appendChild(doc.createTextNode(""+id));
			cuePointElement.appendChild(idElement);
		}
		
		if(name != null){
			Element nameElement = doc.createElement("name");
			nameElement.appendChild(doc.createTextNode(name));
			cuePointElement.appendChild(nameElement);
		}
		
		if(videoId != null){
			Element videoIdElement = doc.createElement("videoId");
			videoIdElement.appendChild(doc.createTextNode(videoId));
			cuePointElement.appendChild(videoIdElement);
		}
		
		if(time != null){
			Element timeElement = doc.createElement("time");
			timeElement.appendChild(doc.createTextNode(""+time));
			cuePointElement.appendChild(timeElement);
		}
		
		if(forceStop != null){
			Element forceStopElement = doc.createElement("forceStop");
			forceStopElement.appendChild(doc.createTextNode(""+forceStop));
			cuePointElement.appendChild(forceStopElement);
		}
		
		if(type != null){
			Element typeElement = doc.createElement("type");
			typeElement.appendChild(doc.createTextNode(""+type));
			cuePointElement.appendChild(typeElement);
		}
		
		if(metadata != null){
			Element metadataElement = doc.createElement("metadata");
			metadataElement.appendChild(doc.createTextNode(metadata));
			cuePointElement.appendChild(metadataElement);
		}
		
		return cuePointElement;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String ret = "[com.brightcove.proserve.mediaapi.wrapper.apiobjects.CuePoint (\n" +
			"\tid:'"        + id        + "'\n" +
			"\tname:'"      + name      + "'\n" +
			"\tvideoId:'"   + videoId   + "'\n" +
			"\ttime:'"      + time      + "'\n" +
			"\tforceStop:'" + forceStop + "'\n" +
			"\ttype:'"      + type      + "'\n" +
			"\tmetadata:'"  + metadata  + "'\n" +
			")]";
		
		return ret;
	}
}

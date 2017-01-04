package data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@IdClass(UserConnectionId.class)
@Entity 
public class UserConnection {
	@Id
	private String userId;
	
	@Id
	private String providerId;
	
	private String providerUserId;
	
	@Id
	private int rank;
	
	private String displayName;
	
	private String profileUrl;
	
	private String imageUrl;
	
	@Column(nullable = false)
	private String accessToken;
	
	private String secret;
	
	private String refreshToken;
	
	private Long expireTime;
	
	public UserConnection(){
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public UserConnection(String userId, String providerId, String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken, String secret, String refreshToken,
			Long expireTime) {
		super();
		this.userId = userId;
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.rank = rank;
		this.displayName = displayName;
		this.profileUrl = profileUrl;
		this.imageUrl = imageUrl;
		this.accessToken = accessToken;
		this.secret = secret;
		this.refreshToken = refreshToken;
		this.expireTime = expireTime;
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserConnection other = (UserConnection) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserConnection [userId=" + userId + ", providerId=" + providerId + ", providerUserId=" + providerUserId
				+ ", rank=" + rank + ", displayName=" + displayName + ", profileUrl=" + profileUrl + ", imageUrl="
				+ imageUrl + ", accessToken=" + accessToken + ", secret=" + secret + ", refreshToken=" + refreshToken
				+ ", expireTime=" + expireTime + "]";
	}
	
	
}

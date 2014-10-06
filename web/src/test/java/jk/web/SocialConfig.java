package jk.web;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.CommentOperations;
import org.springframework.social.facebook.api.EventOperations;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.FqlOperations;
import org.springframework.social.facebook.api.FriendOperations;
import org.springframework.social.facebook.api.GroupOperations;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.LikeOperations;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.OpenGraphOperations;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PlacesOperations;
import org.springframework.social.facebook.api.QuestionOperations;
import org.springframework.social.facebook.api.UserOperations;
import org.springframework.social.linkedin.api.CommunicationOperations;
import org.springframework.social.linkedin.api.CompanyOperations;
import org.springframework.social.linkedin.api.ConnectionOperations;
import org.springframework.social.linkedin.api.JobOperations;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.NetworkUpdateOperations;
import org.springframework.social.linkedin.api.ProfileOperations;
import org.springframework.social.twitter.api.BlockOperations;
import org.springframework.social.twitter.api.DirectMessageOperations;
import org.springframework.social.twitter.api.GeoOperations;
import org.springframework.social.twitter.api.ListOperations;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.StreamingOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

@Configuration
public class SocialConfig {

	@Bean
	public Facebook facebook(){
		return new Facebook() {
			
			@Override
			public boolean isAuthorized() {
				return false;
			}
			
			@Override
			public String publish(String objectId, String connectionName, MultiValueMap<String, Object> data) {
				return null;
			}
			
			@Override
			public void post(String objectId, String connectionName, MultiValueMap<String, String> data) {
			}
			
			@Override
			public <T> T fetchObject(String objectId, Class<T> type, MultiValueMap<String, String> queryParameters) {
				return null;
			}
			
			@Override
			public <T> T fetchObject(String objectId, Class<T> type) {
				return null;
			}
			
			@Override
			public byte[] fetchImage(String objectId, String connectionName, ImageType imageType) {
				return null;
			}
			
			@Override
			public <T> PagedList<T> fetchConnections(String objectId, String connectionName, Class<T> type, MultiValueMap<String, String> queryParameters, String... fields) {
				return null;
			}
			
			@Override
			public <T> PagedList<T> fetchConnections(String objectId, String connectionName, Class<T> type, MultiValueMap<String, String> queryParameters) {
				return null;
			}
			
			@Override
			public <T> PagedList<T> fetchConnections(String objectId, String connectionName, Class<T> type, String... fields) {
				return null;
			}
			
			@Override
			public void delete(String objectId, String connectionName) {
			}
			
			@Override
			public void delete(String objectId) {
			}
			
			@Override
			public UserOperations userOperations() {
				return null;
			}
			
			@Override
			public RestOperations restOperations() {
				return null;
			}
			
			@Override
			public QuestionOperations questionOperations() {
				return null;
			}
			
			@Override
			public PlacesOperations placesOperations() {
				return null;
			}
			
			@Override
			public PageOperations pageOperations() {
				return null;
			}
			
			@Override
			public OpenGraphOperations openGraphOperations() {
				return null;
			}
			
			@Override
			public MediaOperations mediaOperations() {
				return null;
			}
			
			@Override
			public LikeOperations likeOperations() {
				return null;
			}
			
			@Override
			public GroupOperations groupOperations() {
				return null;
			}
			
			@Override
			public String getApplicationNamespace() {
				return null;
			}
			
			@Override
			public FriendOperations friendOperations() {
				return null;
			}
			
			@Override
			public FqlOperations fqlOperations() {
				return null;
			}
			
			@Override
			public FeedOperations feedOperations() {
				return null;
			}
			
			@Override
			public EventOperations eventOperations() {
				return null;
			}
			
			@Override
			public CommentOperations commentOperations() {
				return null;
			}
		};
	}

	@Bean
	public ConnectController getConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository){
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}

	@Bean
	public ConnectionFactoryLocator getConnectionFactoryLocator(){
		return new ConnectionFactoryLocator() {
			
			@Override
			public Set<String> registeredProviderIds() {
				return null;
			}
			
			@Override
			public <A> ConnectionFactory<A> getConnectionFactory(Class<A> apiType) {
				return null;
			}
			
			@Override
			public ConnectionFactory<?> getConnectionFactory(String providerId) {
				return null;
			}
		};
	}

	@Bean
	public ConnectionRepository getConnectionRepository(){
		return new ConnectionRepository() {
			
			@Override
			public void updateConnection(Connection<?> connection) {
			}
			
			@Override
			public void removeConnections(String providerId) {
			}
			
			@Override
			public void removeConnection(ConnectionKey connectionKey) {
			}
			
			@Override
			public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
				return null;
			}
			
			@Override
			public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
				return null;
			}
			
			@Override
			public Connection<?> getConnection(ConnectionKey connectionKey) {
				return null;
			}
			
			@Override
			public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
				return null;
			}
			
			@Override
			public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
				return null;
			}
			
			@Override
			public <A> List<Connection<A>> findConnections(Class<A> apiType) {
				return null;
			}
			
			@Override
			public List<Connection<?>> findConnections(String providerId) {
				return null;
			}
			
			@Override
			public MultiValueMap<String, Connection<?>> findAllConnections() {
				return null;
			}
			
			@Override
			public void addConnection(Connection<?> connection) {
			}
		};
	}

	@Bean
	public Twitter getTwitter(){
		return new Twitter() {
			
			@Override
			public boolean isAuthorized() {
				return false;
			}
			
			@Override
			public org.springframework.social.twitter.api.UserOperations userOperations() {
				return null;
			}
			
			@Override
			public TimelineOperations timelineOperations() {
				return null;
			}
			
			@Override
			public StreamingOperations streamingOperations() {
				return null;
			}
			
			@Override
			public SearchOperations searchOperations() {
				return null;
			}
			
			@Override
			public RestOperations restOperations() {
				return null;
			}
			
			@Override
			public ListOperations listOperations() {
				return null;
			}
			
			@Override
			public GeoOperations geoOperations() {
				return null;
			}
			
			@Override
			public org.springframework.social.twitter.api.FriendOperations friendOperations() {
				return null;
			}
			
			@Override
			public DirectMessageOperations directMessageOperations() {
				return null;
			}
			
			@Override
			public BlockOperations blockOperations() {
				return null;
			}
		};
	}

	@Bean
	public LinkedIn getLinkedIn(){
		return new LinkedIn() {
			
			@Override
			public boolean isAuthorized() {
				return false;
			}
			
			@Override
			public RestOperations restOperations() {
				return null;
			}
			
			@Override
			public ProfileOperations profileOperations() {
				return null;
			}
			
			@Override
			public NetworkUpdateOperations networkUpdateOperations() {
				return null;
			}
			
			@Override
			public JobOperations jobOperations() {
				return null;
			}
			
			@Override
			public org.springframework.social.linkedin.api.GroupOperations groupOperations() {
				return null;
			}
			
			@Override
			public ConnectionOperations connectionOperations() {
				return null;
			}
			
			@Override
			public CompanyOperations companyOperations() {
				return null;
			}
			
			@Override
			public CommunicationOperations communicationOperations() {
				return null;
			}
		};
	}

	@Bean
	public UsersConnectionRepository getUsersConnectionRepository(){
		return new UsersConnectionRepository() {
			
			@Override
			public List<String> findUserIdsWithConnection(Connection<?> connection) {
				return null;
			}
			
			@Override
			public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
				return null;
			}
			
			@Override
			public ConnectionRepository createConnectionRepository(String userId) {
				return null;
			}
		};
	}
}

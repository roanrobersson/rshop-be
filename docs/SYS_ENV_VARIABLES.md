[< Back](../README.md)

# System environment variables :desktop_computer:

### Global
<table>
<thead>
<tr>
	<th></th>
	<th>Variable name</th>
	<th>Default value</th>
</tr>
</thead>
<tbody>
<tr>
	<td rowspan=1>Active Profile</td>
	<td>RSHOP_ACTIVE_PROFILE</td>
	<td>dev</td>
</tr>
<tr>
	<td rowspan=1>Web server</td>
	<td>RSHOP_SERVER_PORT</td>
	<td>8080</td>
</tr>
<tr>
	<td rowspan=4>AWS</td>
	<td>RSHOP_AWS_SECRET </td>
	<td>empty</td>
</tr>
<tr>
	<td>RSHOP_AWS_KEY</td>
	<td>empty</td>
</tr>
<tr>
	<td>RSHOP_AWS_BUCKET_NAME</td>
	<td>empty</td>
</tr>
<tr>
	<td>RSHOP_AWS_BUCKET_REGION</td>
	<td>sa-east-1</td>
</tr>
<tr>
<td rowspan=2>File upload</td>
	<td>RSHOP_MAX_FILE_SIZE</td>
	<td>10</td>
</tr>
<tr>
	<td>RSHOP_MAX_FILE_SIZE</td>
	<td>10</td>
</tr>
<tr>
	<td rowspan=3>Email</td>
	<td>RSHOP_SENDGRID_API_KEY</td>
<td>empty</td>
</tr>
<tr>
	<td>RSHOP_SENDGRID_SENDER_NAME</td>
	<td>rShop</td>
</tr>
<tr>
	<td>RSHOP_SENDERGRID_EMAIL</td>
	<td>empty</td>
</tr>


</tbody>
</table>

### Developer and Production
<table>
<thead>
<tr>
	<th></th>
	<th>Variable name</th>
	<th>Default value</th>
</tr>
</thead>
<tbody>
<tr>
	<td rowspan=3>Datasource</td>
	<td>RSHOP_DATASOURCE_HOST</td>
	<td>localhost:3306</td>
</tr>
<tr>
	<td>RSHOP_DATASOURCE_USERNAME</td>
	<td>root</td>
</tr>
<tr>
	<td>RSHOP_DATASOURCE_PASSWORD</td>
	<td></td>
</tr>

<tr>
	<td rowspan=5>Security</td>
	<td>RSHOP_CLIENT_ID</td>
	<td>rshop</td>
</tr>
<tr>
	<td>RSHOP_CLIENT_SECRET</td>
	<td>rshopsecret</td>
</tr>
<tr>
	<td>RSHOP_JWT_SECRET</td>
	<td>MY-JWT-SECRET</td>
</tr>
<tr>
	<td>RSHOP_JWT_DURATION</td>
	<td>900</td>
</tr>
<tr>
	<td>RSHOP_JWT_REFRESH_TOKEN_DURATION</td>
	<td>86400</td>
</tr>

<tr>
	<td rowspan=4>OpenApi</td>
	<td>RSHOP_SWAGGER_ENABLED</td>
	<td>true</td>
</tr>
<tr>
	<td>RSHOP_CLIENT_ID</td>
	<td>rshop</td>
</tr>
<tr>
	<td>RSHOP_CLIENT_SECRET</td>
	<td>rshopsecret</td>
</tr>
<tr>
	<td>RSHOP_SERVER_PORT</td>
	<td>8080</td>
</tr>
</tbody>
</table>

### Integration tests
<table>
<thead>
<tr>
	<th></th>
	<th>Variable name</th>
	<th>Default value</th>
</tr>
</thead>
<tbody>
<tr>
	<td rowspan=4>Datasource</td>
	<td>RSHOP_DATASOURCE_HOST</td>
	<td>localhost:3306</td>
</tr>
<tr>
	<td>RSHOP_DATASOURCE_USERNAME</td>
	<td>root</td>
</tr>
<tr>
	<td>RSHOP_DATASOURCE_PASSWORD</td>
	<td></td>
</tr>
<tr>
	<td>RSHOP_TESTCONTAINERS_ENABLED</td>
	<td>false</td>
</tr>

<tr>
	<td rowspan=5>Security</td>
	<td>RSHOP_CLIENT_ID</td>
	<td>rshop</td>
</tr>
<tr>
	<td>RSHOP_CLIENT_SECRET</td>
	<td>rshopsecret</td>
</tr>
<tr>
	<td>RSHOP_JWT_SECRET</td>
	<td>MY-JWT-SECRET</td>
</tr>
<tr>
	<td>RSHOP_JWT_DURATION</td>
	<td>900</td>
</tr>
<tr>
	<td>RSHOP_JWT_REFRESH_TOKEN_DURATION</td>
	<td>86400</td>
</tr>
</tbody>
</table>

### Unit tests
None at the moment

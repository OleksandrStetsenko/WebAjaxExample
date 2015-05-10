<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<script src="${pageContext.request.contextPath}/jquery-2.1.1.js"></script>
</head>
        <script>
                function submit() {
                        jQuery.post('${pageContext.request.contextPath}/messages', {
                                'username': jQuery('#userField').val(),
                                'message':  jQuery('#messageField').val()
                        });
                        jQuery('#messageField').val('');
                }
                var messageDates = { };
                window.setInterval(function() {
                        jQuery.get('${pageContext.request.contextPath}/messages', function(messages) {
                                jQuery.each(messages, function(i, message) {
                                        if ( ! messageDates[message.posted] ) {
                                            messageDates[message.posted] = true;
                                            jQuery('#messages').append(jQuery(
                                                '<li><b>'+ message.username +'</b>: '+ 
                                                        message.message +'</li>'));
                                        }
                                }); 
                        });
                }, 5000);
        </script>
<body>

<h1>Messages</h1>
<ol id="messages">
</ol>
<p>
   <input id="userField" size="20"/>:
   <input id="messageField" size="100"/>
   <button onclick="submit()">Post</button>
</p>

</body>

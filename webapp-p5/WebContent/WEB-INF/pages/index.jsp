<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<%@ include file="common.jsp"%>

<title>Welcome To The Shape Calculator</title>

	<script>
	function loadNewReqFrame() {
		$("#requestFrameDiv").html("<iframe src=\'${pageContext.request.contextPath}/newreq\'></iframe>");
	}
	</script>

	<script>
	function loadCurReqFrame() {
		$("#pendingFrameDiv").html("<iframe src=\'${pageContext.request.contextPath}/pending\'></iframe>");
	}
	</script>

	<script>
	function loadResultsFrame() {
		$("#resultsFrameDiv").html("<iframe src=\'${pageContext.request.contextPath}/results\'></iframe>");
	}
	</script>

</head>
<body>
	<div class="container">

		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#home">Home</a></li>
			<li><a data-toggle="tab" href="#about" >Usage</a></li>
			<li><a data-toggle="tab" href="#newreq" onClick="loadNewReqFrame()">Request Calculation</a></li>
			<li><a data-toggle="tab" href="#pending" onClick="loadCurReqFrame()" >Pending Requests</a></li>
			<li><a data-toggle="tab" href="#results" onClick="loadResultsFrame()">Calculated Results</a></li>
		</ul>

		<div class="tab-content">

<!------------------------------------------------------------------------------------>
<!------------------------------------------------------------------------------------>
			<div id="home" class="tab-pane fade in active">
				<h3>HOME</h3>
				<p>Wecome to The Shape Calculator</p>
				<p/>
				<p>
				This is a project that began as a result of a programming exercise.<br>
				It is probably non-sensical, but it was a good way to review/study<br>
				some technologies such as:
				</p>
				<p>HTML, CSS, JS, Bootstrap</p>
				<p>Spring MVC</p>
				<p>JPA / Hibernate / MySQL</p>
				<p>This is a complete web app, front to back</p>
				<p>(there is another that is a web app client consuming a web service)</p>
			</div>

<!------------------------------------------------------------------------------------>
<!------------------------------------------------------------------------------------>
			<div id="about" class="tab-pane fade ">
				<h3>About/Usage</h3>
				<p>Wecome to The Shape Calculator</p>
				<p/>
				<p>
				If you first click on either <b>Pending Requests</b> or <b>Calculated Results</b>,<br>
				you should not see much.  Once you do a <b>Request Calculation</b>, then <br>
				the requested calculation moves to a list of Pending Requests. Once you do a <br>
				<b>Run All Pending Requests</b> then the Pending Requests will disappear,<br>
				and will have moved to Calculated Results.<br>
				<br>
				You can request multiple calculations<br>
				and then run them all at the same time.<br>
				<br>
				I purposely put in some random time for each calculation, to simulate<br>
				a long-running process.  I did not have time to add a 'busy' icon<br>
				in the Run/List page.. so you may just see a delay before anything<br>
				appears.
				</p>
				<p>Tested on Chrome 51.0.., IE 11.0.., and Firefox 37.0..
			</div>


<!------------------------------------------------------------------------------------>
<!------------------------------------------------------------------------------------>
			<div id="newreq" class="tab-pane fade">
				<h3>Request Calculation</h3>
<!--  -->
				<div id="requestFrameDiv" class="embed-responsive embed-responsive-16by9">
				</div>
<!--  -->
			</div>

<!------------------------------------------------------------------------------------>
<!------------------------------------------------------------------------------------>
			<div id="pending" class="tab-pane fade">
				<h3>Pending Requests</h3>
<!--  -->
				<div id="pendingFrameDiv" class="embed-responsive embed-responsive-16by9">
				</div>
<!--  -->
			</div>

<!------------------------------------------------------------------------------------>
<!------------------------------------------------------------------------------------>
			<div id="results" class="tab-pane fade">
				<h3>Calculated Results</h3>
<!--  -->
				<div id="resultsFrameDiv" class="embed-responsive embed-responsive-16by9">
				</div>
<!--  -->
			</div>


		</div>

	</div>

</body>
</html>
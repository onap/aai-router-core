/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2017-2018 Amdocs
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.aai.event;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Endpoint;
import org.apache.camel.ErrorHandlerFactory;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.NoFactoryAvailableException;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.ServiceStatus;
import org.apache.camel.ShutdownRoute;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.StartupListener;
import org.apache.camel.TypeConverter;
import org.apache.camel.api.management.mbean.ManagedCamelContextMBean;
import org.apache.camel.api.management.mbean.ManagedProcessorMBean;
import org.apache.camel.api.management.mbean.ManagedRouteMBean;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.health.HealthCheckRegistry;
import org.apache.camel.impl.DefaultHeadersMapFactory;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.model.HystrixConfigurationDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.model.cloud.ServiceCallConfigurationDefinition;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestsDefinition;
import org.apache.camel.model.transformer.TransformerDefinition;
import org.apache.camel.model.validator.ValidatorDefinition;
import org.apache.camel.runtimecatalog.RuntimeCamelCatalog;
import org.apache.camel.spi.AsyncProcessorAwaitManager;
import org.apache.camel.spi.CamelContextNameStrategy;
import org.apache.camel.spi.ClassResolver;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.DataFormatResolver;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.Debugger;
import org.apache.camel.spi.EndpointRegistry;
import org.apache.camel.spi.EndpointStrategy;
import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.camel.spi.ExecutorServiceStrategy;
import org.apache.camel.spi.FactoryFinder;
import org.apache.camel.spi.FactoryFinderResolver;
import org.apache.camel.spi.HeadersMapFactory;
import org.apache.camel.spi.InflightRepository;
import org.apache.camel.spi.Injector;
import org.apache.camel.spi.InterceptStrategy;
import org.apache.camel.spi.Language;
import org.apache.camel.spi.LifecycleStrategy;
import org.apache.camel.spi.LogListener;
import org.apache.camel.spi.ManagementMBeanAssembler;
import org.apache.camel.spi.ManagementNameStrategy;
import org.apache.camel.spi.ManagementStrategy;
import org.apache.camel.spi.MessageHistoryFactory;
import org.apache.camel.spi.ModelJAXBContextFactory;
import org.apache.camel.spi.NodeIdFactory;
import org.apache.camel.spi.PackageScanClassResolver;
import org.apache.camel.spi.ProcessorFactory;
import org.apache.camel.spi.Registry;
import org.apache.camel.spi.ReloadStrategy;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestRegistry;
import org.apache.camel.spi.RouteController;
import org.apache.camel.spi.RoutePolicyFactory;
import org.apache.camel.spi.RouteStartupOrder;
import org.apache.camel.spi.RuntimeEndpointRegistry;
import org.apache.camel.spi.ServicePool;
import org.apache.camel.spi.ShutdownStrategy;
import org.apache.camel.spi.StreamCachingStrategy;
import org.apache.camel.spi.Transformer;
import org.apache.camel.spi.TransformerRegistry;
import org.apache.camel.spi.TypeConverterRegistry;
import org.apache.camel.spi.UnitOfWorkFactory;
import org.apache.camel.spi.UuidGenerator;
import org.apache.camel.spi.Validator;
import org.apache.camel.spi.ValidatorRegistry;
import org.apache.camel.util.LoadPropertiesException;
import org.apache.camel.util.jsse.SSLContextParameters;

public class TestCamelContext implements CamelContext {

    @Override
    public void suspend() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isSuspended() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setStreamCaching(Boolean cache) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isStreamCaching() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTracing(Boolean tracing) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isTracing() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMessageHistory(Boolean messageHistory) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isMessageHistory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setHandleFault(Boolean handleFault) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isHandleFault() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDelayer(Long delay) {
        // TODO Auto-generated method stub

    }

    @Override
    public Long getDelayer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAutoStartup(Boolean autoStartup) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isAutoStartup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setShutdownRoute(ShutdownRoute shutdownRoute) {
        // TODO Auto-generated method stub

    }

    @Override
    public ShutdownRoute getShutdownRoute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setShutdownRunningTask(ShutdownRunningTask shutdownRunningTask) {
        // TODO Auto-generated method stub

    }

    @Override
    public ShutdownRunningTask getShutdownRunningTask() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAllowUseOriginalMessage(Boolean allowUseOriginalMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isAllowUseOriginalMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends CamelContext> T adapt(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CamelContextNameStrategy getNameStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNameStrategy(CamelContextNameStrategy nameStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public ManagementNameStrategy getManagementNameStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setManagementNameStrategy(ManagementNameStrategy nameStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getManagementName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceStatus getStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUptime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addService(Object object) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void addService(Object object, boolean closeOnShutdown) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean removeService(Object object) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasService(Object object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T hasService(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addStartupListener(StartupListener listener) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void addComponent(String componentName, Component component) {
        // TODO Auto-generated method stub

    }

    @Override
    public Component hasComponent(String componentName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Component getComponent(String componentName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Component getComponent(String name, boolean autoCreateComponents) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Component> T getComponent(String name, Class<T> componentType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getComponentNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Component removeComponent(String componentName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EndpointRegistry<String> getEndpointRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Endpoint getEndpoint(String uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Endpoint> T getEndpoint(String name, Class<T> endpointType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Endpoint> getEndpoints() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Endpoint> getEndpointMap() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Endpoint hasEndpoint(String uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Endpoint addEndpoint(String uri, Endpoint endpoint) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeEndpoint(Endpoint endpoint) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<Endpoint> removeEndpoints(String pattern) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addRegisterEndpointCallback(EndpointStrategy strategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setupRoutes(boolean done) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<RouteDefinition> getRouteDefinitions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RouteDefinition getRouteDefinition(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RestDefinition> getRestDefinitions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addRestDefinitions(Collection<RestDefinition> restDefinitions) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRestConfiguration(RestConfiguration restConfiguration) {
        // TODO Auto-generated method stub

    }

    @Override
    public RestConfiguration getRestConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<RouteStartupOrder> getRouteStartupOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Route> getRoutes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Route getRoute(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addRoutes(RoutesBuilder builder) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public RoutesDefinition loadRoutesDefinition(InputStream is) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addRouteDefinitions(Collection<RouteDefinition> routeDefinitions) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRouteDefinition(RouteDefinition routeDefinition) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeRouteDefinitions(Collection<RouteDefinition> routeDefinitions) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeRouteDefinition(RouteDefinition routeDefinition) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void startRoute(RouteDefinition route) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void startAllRoutes() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void startRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopRoute(RouteDefinition route) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopRoute(String routeId, long timeout, TimeUnit timeUnit) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean stopRoute(String routeId, long timeout, TimeUnit timeUnit, boolean abortAfterTimeout)
            throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void shutdownRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void shutdownRoute(String routeId, long timeout, TimeUnit timeUnit) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean removeRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resumeRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void suspendRoute(String routeId) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void suspendRoute(String routeId, long timeout, TimeUnit timeUnit) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ServiceStatus getRouteStatus(String routeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isStartingRoutes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSetupRoutes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TypeConverter getTypeConverter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeConverterRegistry getTypeConverterRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Registry getRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getRegistry(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Injector getInjector() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ManagementMBeanAssembler getManagementMBeanAssembler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LifecycleStrategy> getLifecycleStrategies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addLifecycleStrategy(LifecycleStrategy lifecycleStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public Language resolveLanguage(String language) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String resolvePropertyPlaceholders(String text) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPropertyPrefixToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPropertySuffixToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getLanguageNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProducerTemplate createProducerTemplate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProducerTemplate createProducerTemplate(int maximumCacheSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConsumerTemplate createConsumerTemplate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConsumerTemplate createConsumerTemplate(int maximumCacheSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addInterceptStrategy(InterceptStrategy interceptStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<InterceptStrategy> getInterceptStrategies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorHandlerBuilder getErrorHandlerBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setErrorHandlerBuilder(ErrorHandlerFactory errorHandlerBuilder) {
        // TODO Auto-generated method stub

    }

    @Override
    public ScheduledExecutorService getErrorHandlerExecutorService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDataFormats(Map<String, DataFormatDefinition> dataFormats) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, DataFormatDefinition> getDataFormats() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataFormat resolveDataFormat(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataFormatDefinition resolveDataFormatDefinition(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataFormatResolver getDataFormatResolver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDataFormatResolver(DataFormatResolver dataFormatResolver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProperties(Map<String, String> properties) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, String> getProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProperty(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FactoryFinder getDefaultFactoryFinder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFactoryFinderResolver(FactoryFinderResolver resolver) {
        // TODO Auto-generated method stub

    }

    @Override
    public FactoryFinder getFactoryFinder(String path) throws NoFactoryAvailableException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClassResolver getClassResolver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PackageScanClassResolver getPackageScanClassResolver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setClassResolver(ClassResolver resolver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPackageScanClassResolver(PackageScanClassResolver resolver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProducerServicePool(ServicePool<Endpoint, Producer> servicePool) {
        // TODO Auto-generated method stub

    }

    @Override
    public ServicePool<Endpoint, Producer> getProducerServicePool() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPollingConsumerServicePool(ServicePool<Endpoint, PollingConsumer> servicePool) {
        // TODO Auto-generated method stub

    }

    @Override
    public ServicePool<Endpoint, PollingConsumer> getPollingConsumerServicePool() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNodeIdFactory(NodeIdFactory factory) {
        // TODO Auto-generated method stub

    }

    @Override
    public NodeIdFactory getNodeIdFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ManagementStrategy getManagementStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setManagementStrategy(ManagementStrategy strategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public InterceptStrategy getDefaultTracer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultTracer(InterceptStrategy tracer) {
        // TODO Auto-generated method stub

    }

    @Override
    public InterceptStrategy getDefaultBacklogTracer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultBacklogTracer(InterceptStrategy backlogTracer) {
        // TODO Auto-generated method stub

    }

    @Override
    public InterceptStrategy getDefaultBacklogDebugger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultBacklogDebugger(InterceptStrategy backlogDebugger) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableJMX() throws IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public InflightRepository getInflightRepository() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInflightRepository(InflightRepository repository) {
        // TODO Auto-generated method stub

    }

    @Override
    public AsyncProcessorAwaitManager getAsyncProcessorAwaitManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAsyncProcessorAwaitManager(AsyncProcessorAwaitManager manager) {
        // TODO Auto-generated method stub

    }

    @Override
    public ClassLoader getApplicationContextClassLoader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setApplicationContextClassLoader(ClassLoader classLoader) {
        // TODO Auto-generated method stub

    }

    @Override
    public ShutdownStrategy getShutdownStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setShutdownStrategy(ShutdownStrategy shutdownStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public ExecutorServiceManager getExecutorServiceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExecutorServiceStrategy getExecutorServiceStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setExecutorServiceManager(ExecutorServiceManager executorServiceManager) {
        // TODO Auto-generated method stub

    }

    @Override
    public ProcessorFactory getProcessorFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProcessorFactory(ProcessorFactory processorFactory) {
        // TODO Auto-generated method stub

    }

    @Override
    public Debugger getDebugger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDebugger(Debugger debugger) {
        // TODO Auto-generated method stub

    }

    @Override
    public UuidGenerator getUuidGenerator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUuidGenerator(UuidGenerator uuidGenerator) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isLazyLoadTypeConverters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLazyLoadTypeConverters(Boolean lazyLoadTypeConverters) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isTypeConverterStatisticsEnabled() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTypeConverterStatisticsEnabled(Boolean typeConverterStatisticsEnabled) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isUseMDCLogging() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUseMDCLogging(Boolean useMDCLogging) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isUseBreadcrumb() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUseBreadcrumb(Boolean useBreadcrumb) {
        // TODO Auto-generated method stub

    }

    @Override
    public String resolveComponentDefaultName(String javaType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Properties> findComponents() throws LoadPropertiesException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Properties> findEips() throws LoadPropertiesException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getComponentDocumentation(String componentName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getComponentParameterJsonSchema(String componentName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDataFormatParameterJsonSchema(String dataFormatName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLanguageParameterJsonSchema(String languageName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEipParameterJsonSchema(String eipName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String explainEipJson(String nameOrId, boolean includeAllOptions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String explainComponentJson(String componentName, boolean includeAllOptions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String explainEndpointJson(String uri, boolean includeAllOptions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createRouteStaticEndpointJson(String routeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createRouteStaticEndpointJson(String routeId, boolean includeDynamic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StreamCachingStrategy getStreamCachingStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStreamCachingStrategy(StreamCachingStrategy streamCachingStrategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public UnitOfWorkFactory getUnitOfWorkFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUnitOfWorkFactory(UnitOfWorkFactory unitOfWorkFactory) {
        // TODO Auto-generated method stub

    }

    @Override
    public RuntimeEndpointRegistry getRuntimeEndpointRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRuntimeEndpointRegistry(RuntimeEndpointRegistry runtimeEndpointRegistry) {
        // TODO Auto-generated method stub

    }

    @Override
    public RestRegistry getRestRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRestRegistry(RestRegistry restRegistry) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRoutePolicyFactory(RoutePolicyFactory routePolicyFactory) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<RoutePolicyFactory> getRoutePolicyFactories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModelJAXBContextFactory getModelJAXBContextFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModelJAXBContextFactory(ModelJAXBContextFactory modelJAXBContextFactory) {
        // TODO Auto-generated method stub

    }

    @Override
    public Boolean isLogExhaustedMessageBody() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isLogMask() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLogExhaustedMessageBody(Boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLogMask(Boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addHystrixConfiguration(String arg0, HystrixConfigurationDefinition arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addLogListener(LogListener arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addRestConfiguration(RestConfiguration arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addService(Object arg0, boolean arg1, boolean arg2) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addServiceCallConfiguration(String arg0, ServiceCallConfigurationDefinition arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public DataFormat createDataFormat(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FluentProducerTemplate createFluentProducerTemplate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FluentProducerTemplate createFluentProducerTemplate(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deferStartService(Object arg0, boolean arg1) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String explainDataFormatJson(String arg0, DataFormat arg1, boolean arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Component getComponent(String arg0, boolean arg1, boolean arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getGlobalOption(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getGlobalOptions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HeadersMapFactory getHeadersMapFactory() {
        return new DefaultHeadersMapFactory();
    }

    @Override
    public HealthCheckRegistry getHealthCheckRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HystrixConfigurationDefinition getHystrixConfiguration(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<LogListener> getLogListeners() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ManagedCamelContextMBean getManagedCamelContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends ManagedProcessorMBean> T getManagedProcessor(String arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends ManagedRouteMBean> T getManagedRoute(String arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageHistoryFactory getMessageHistoryFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Processor getProcessor(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends Processor> T getProcessor(String arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProcessorDefinition getProcessorDefinition(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends ProcessorDefinition> T getProcessorDefinition(String arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReloadStrategy getReloadStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RestConfiguration getRestConfiguration(String arg0, boolean arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<RestConfiguration> getRestConfigurations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RouteController getRouteController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RuntimeCamelCatalog getRuntimeCamelCatalog() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SSLContextParameters getSSLContextParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServiceCallConfigurationDefinition getServiceCallConfiguration(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TransformerRegistry getTransformerRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TransformerDefinition> getTransformers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getUptimeMillis() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ValidatorRegistry getValidatorRegistry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidatorDefinition> getValidators() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> Set<T> hasServices(Class<T> arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isLoadTypeConverters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isUseDataType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isVetoStarted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public RestsDefinition loadRestsDefinition(InputStream arg0) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transformer resolveTransformer(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transformer resolveTransformer(DataType arg0, DataType arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Validator resolveValidator(DataType arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setGlobalOptions(Map<String, String> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHeadersMapFactory(HeadersMapFactory arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHealthCheckRegistry(HealthCheckRegistry arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHystrixConfiguration(HystrixConfigurationDefinition arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHystrixConfigurations(List<HystrixConfigurationDefinition> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLoadTypeConverters(Boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setMessageHistoryFactory(MessageHistoryFactory arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setReloadStrategy(ReloadStrategy arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setRouteController(RouteController arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSSLContextParameters(SSLContextParameters arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setServiceCallConfiguration(ServiceCallConfigurationDefinition arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setServiceCallConfigurations(List<ServiceCallConfigurationDefinition> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTransformers(List<TransformerDefinition> arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setUseDataType(Boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setValidators(List<ValidatorDefinition> arg0) {
        // TODO Auto-generated method stub
        
    }

}

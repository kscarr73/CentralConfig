/*
 Licenced under Apache 2.0 License

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
package com.progbits.config.impl;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.ConfigurationPolicy;
import aQute.bnd.annotation.component.Deactivate;
import aQute.bnd.annotation.component.Modified;
import com.progbits.config.CentralConfig;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.osgi.framework.BundleContext;

/**
 *
 * @author scarr
 */
@Component(name = "centralconfig", properties = {"name=centralconfig"}, immediate = true,
        configurationPolicy = ConfigurationPolicy.require)
public class CentralConfigImpl implements CentralConfig {

    private BundleContext _context = null;
    private final Map<String, String> configSettings = new HashMap<>();

    @Activate
    public void start(BundleContext context, Map<String, Object> config) throws Exception {
        _context = context;

        // This uses the pre 4.0 command logic
        Dictionary<String, Object> cmdProps = new Hashtable<>();
        cmdProps.put("osgi.command.scope", "centralconfig");
        cmdProps.put("osgi.command.function", new String[]{"listEntries"});
        _context.registerService(CentralConfigImpl.class, this, cmdProps);

        updated(config);
    }

    @Deactivate
    public void stop(BundleContext context) throws Exception {
        // Unregister all Services

    }

    @Modified
    public void updated(Map<String, Object> dctnr) {
        if (dctnr != null) {
            for (Map.Entry<String, Object> entry : dctnr.entrySet()) {
                String sKey = entry.getKey();

                if (entry.getValue() instanceof String) {
                    configSettings.put(entry.getKey(), (String) entry.getValue());
                }
            }
        }
    }

    public void listEntries() {
        for (Map.Entry<String, String> entry : configSettings.entrySet()) {
            System.out.print(String.format("%1$15s", entry.getKey()));
            System.out.print(String.format("%1$15s", entry.getValue()));
            System.out.print("\n");
        }
    }

    @Override
    public String getProperty(String key) {
        return configSettings.get(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String retVal = configSettings.get(key);

        if (retVal == null) {
            retVal = defaultValue;
        }

        return retVal;
    }

}
